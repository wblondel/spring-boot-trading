package com.williamblondel.infinitrade.controller;

import com.williamblondel.infinitrade.assembler.TradeModelAssembler;
import com.williamblondel.infinitrade.exception.TradeNotFoundException;
import com.williamblondel.infinitrade.model.Trade;
import com.williamblondel.infinitrade.repository.TradeRepository;
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
public class TradeController {
    private final TradeRepository repository;
    private final TradeModelAssembler assembler;

    public TradeController(TradeRepository repository, TradeModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping({"/trades", "/trades/"})
    public CollectionModel<EntityModel<Trade>> all() {
        List<EntityModel<Trade>> trades = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                trades,
                linkTo(methodOn(TradeController.class)
                        .all()
                ).withSelfRel()
        );
    }
    // end::get-aggregate-root[]

    @GetMapping("/trades/{id}")
    public EntityModel<Trade> one(@PathVariable Long id) {
        Trade trade = repository.findById(id)
                .orElseThrow(() -> new TradeNotFoundException(id));

        return assembler.toModel(trade);
    }
}
