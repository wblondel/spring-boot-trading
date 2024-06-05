package com.williamblondel.infinitrade.controller;

import com.williamblondel.infinitrade.assembler.WalletModelAssembler;
import com.williamblondel.infinitrade.exception.UserNotFoundException;
import com.williamblondel.infinitrade.exception.WalletNotFoundException;
import com.williamblondel.infinitrade.model.User;
import com.williamblondel.infinitrade.model.Wallet;
import com.williamblondel.infinitrade.repository.UserRepository;
import com.williamblondel.infinitrade.repository.WalletRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserWalletController {
    private final WalletRepository repository;
    private final WalletModelAssembler assembler;
    private final UserRepository userRepository;

    public UserWalletController(WalletRepository repository, WalletModelAssembler assembler, UserRepository userRepository) {
        this.repository = repository;
        this.assembler = assembler;
        this.userRepository = userRepository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping({"/users/{userId}/wallets", "/users/{userId}/wallets/"})
    public CollectionModel<EntityModel<Wallet>> all(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        List<EntityModel<Wallet>> wallets = user.getWallets().stream()
                .map(wallet -> assembler.toModel(wallet, false))
                .collect(Collectors.toList());

        return CollectionModel.of(
                wallets,
                linkTo(methodOn(UserWalletController.class).all(userId)).withSelfRel()
        );
    }
    // end::get-aggregate-root[]

    @GetMapping({"/me/wallets", "/me/wallets/"})
    public CollectionModel<EntityModel<Wallet>> meAll() {
        // We assume the User 1 has already authenticated
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new UserNotFoundException(1L));

        List<EntityModel<Wallet>> wallets = user.getWallets().stream()
                .map(wallet -> assembler.toModel(wallet, false))
                .collect(Collectors.toList());

        return CollectionModel.of(
                wallets,
                linkTo(methodOn(UserWalletController.class).all(user.getId())).withSelfRel()
        );
    }
}
