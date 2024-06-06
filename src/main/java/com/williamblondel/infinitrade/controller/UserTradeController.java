package com.williamblondel.infinitrade.controller;

import com.williamblondel.infinitrade.assembler.TradeModelAssembler;
import com.williamblondel.infinitrade.exception.UserNotFoundException;
import com.williamblondel.infinitrade.model.Trade;
import com.williamblondel.infinitrade.model.User;
import com.williamblondel.infinitrade.repository.TradeRepository;
import com.williamblondel.infinitrade.repository.UserRepository;
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
public class UserTradeController {
    private final TradeRepository repository;
    private final TradeModelAssembler assembler;
    private final UserRepository userRepository;

    public UserTradeController(TradeRepository repository, TradeModelAssembler assembler,
                               UserRepository userRepository) {
        this.repository = repository;
        this.assembler = assembler;
        this.userRepository = userRepository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping({"/users/{userId}/trades", "/users/{userId}/trades/"})
    public CollectionModel<EntityModel<Trade>> all(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        List<EntityModel<Trade>> trades = user.getTrades().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                trades,
                linkTo(methodOn(UserTradeController.class)
                        .all(userId)
                ).withSelfRel()
        );
    }
    // end::get-aggregate-root[]

    @GetMapping({"/me/trades", "/me/trades/"})
    public CollectionModel<EntityModel<Trade>> meAll() {
        // We assume the User 1 has already authenticated
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new UserNotFoundException(1L));

        List<EntityModel<Trade>> trades = user.getTrades().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                trades,
                linkTo(methodOn(UserTradeController.class)
                        .all(user.getId())
                ).withSelfRel()
        );
    }
}
