package com.williamblondel.infinitrade.controller;

import com.williamblondel.infinitrade.assembler.WalletModelAssembler;
import com.williamblondel.infinitrade.exception.WalletNotFoundException;
import com.williamblondel.infinitrade.model.Wallet;
import com.williamblondel.infinitrade.repository.WalletRepository;
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
public class WalletController {
    private final WalletRepository repository;
    private final WalletModelAssembler assembler;

    public WalletController(WalletRepository repository, WalletModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping({"/wallets", "/wallets/"})
    public CollectionModel<EntityModel<Wallet>> all() {
        List<EntityModel<Wallet>> wallets = repository.findAll().stream()
                .map(wallet -> assembler.toModel(wallet, true))
                .collect(Collectors.toList());

        return CollectionModel.of(
                wallets,
                linkTo(methodOn(WalletController.class)
                        .all()
                ).withSelfRel()
        );
    }
    // end::get-aggregate-root[]
    @GetMapping("/wallets/{id}")
    public EntityModel<Wallet> one(@PathVariable Long id) {
        Wallet wallet = repository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException(id));

        return assembler.toModel(wallet);
    }
}
