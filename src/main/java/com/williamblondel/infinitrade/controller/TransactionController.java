package com.williamblondel.infinitrade.controller;

import com.williamblondel.infinitrade.assembler.TransactionModelAssembler;
import com.williamblondel.infinitrade.exception.TransactionNotFoundException;
import com.williamblondel.infinitrade.model.Transaction;
import com.williamblondel.infinitrade.repository.TransactionRepository;
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
public class TransactionController {
    private final TransactionRepository repository;
    private final TransactionModelAssembler assembler;

    public TransactionController(TransactionRepository repository, TransactionModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping({"/transactions", "/transactions/"})
    public CollectionModel<EntityModel<Transaction>> all() {
        List<EntityModel<Transaction>> transactions = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                transactions,
                linkTo(methodOn(TransactionController.class)
                        .all()
                ).withSelfRel()
        );
    }
    // end::get-aggregate-root[]

    @GetMapping("/transactions/{id}")
    public EntityModel<Transaction> one(@PathVariable Long id) {
        Transaction transaction = repository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));

        return assembler.toModel(transaction);
    }
}
