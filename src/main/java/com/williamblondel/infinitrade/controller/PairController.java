package com.williamblondel.infinitrade.controller;

import com.williamblondel.infinitrade.assembler.PairModelAssembler;
import com.williamblondel.infinitrade.exception.PairNotFoundException;
import com.williamblondel.infinitrade.model.Pair;
import com.williamblondel.infinitrade.repository.PairRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        return CollectionModel.of(pairs, linkTo(methodOn(PairController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

//    @PostMapping({"/pairs", "/pairs/"})
//    ResponseEntity<?> newPair(@RequestBody Pair newPair) {
//        EntityModel<Pair> entityModel = assembler.toModel(repository.save(newPair));
//
//        return ResponseEntity
//                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
//                .body(entityModel);
//    }

    @GetMapping("/pairs/{id}")
    public EntityModel<Pair> one(@PathVariable Long id) {

        Pair pair = repository.findById(id)
                .orElseThrow(() -> new PairNotFoundException(id));

        return assembler.toModel(pair);
    }

//    @PutMapping("/pairs/{id}")
//    ResponseEntity<?> replacePair(@PathVariable Long id, @RequestBody Pair newPair) {
//        Pair updatedPair = repository.findById(id)
//                .map(pair -> {
//                    //pair.setPricePrecision(newPair.getPricePrecision());
//                   // pair.setQuantityPrecision(newPair.getQuantityPrecision());
//                   // pair.setMinimumQuantity(newPair.getMinimumQuantity());
//
//                    return repository.save(pair);
//                })
//                .orElseGet(() -> {
//                    newPair.setId(id);
//                    return repository.save(newPair);
//                });
//
//        EntityModel<Pair> entityModel = assembler.toModel(updatedPair);
//
//        return ResponseEntity
//                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
//                .body(entityModel);
//    }

//    @DeleteMapping("/pairs/{id}")
//    ResponseEntity<?> deletePair(@PathVariable Long id) {
//        repository.deleteById(id);
//
//        return ResponseEntity.noContent().build();
//    }
}
