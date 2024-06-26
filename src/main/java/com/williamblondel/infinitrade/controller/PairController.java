package com.williamblondel.infinitrade.controller;

import com.williamblondel.infinitrade.assembler.PairModelAssembler;
import com.williamblondel.infinitrade.exception.PairNotFoundException;
import com.williamblondel.infinitrade.model.Pair;
import com.williamblondel.infinitrade.repository.PairRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class PairController {
    private final PairRepository repository;

    private final PairModelAssembler assembler;

    PairController(PairRepository repository, PairModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping({"/pairs", "/pairs/"})
    public CollectionModel<EntityModel<Pair>> all() {
        List<EntityModel<Pair>> pairs = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                pairs,
                linkTo(methodOn(PairController.class)
                        .all()
                ).withSelfRel());
    }
    // end::get-aggregate-root[]

    @GetMapping("/pairs/{pairCode}")
    public EntityModel<Pair> one(@PathVariable String pairCode) {

        Pair pair = repository.findByPairCode(pairCode)
                .orElseThrow(() -> new PairNotFoundException(pairCode));

        return assembler.toModel(pair);
    }
}
