package com.williamblondel.infinitrade.controller;

import com.williamblondel.infinitrade.assembler.UserModelAssembler;
import com.williamblondel.infinitrade.exception.UserNotFoundException;
import com.williamblondel.infinitrade.model.User;
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
public class UserController {
    private final UserRepository repository;
    private final UserModelAssembler assembler;

    public UserController(UserRepository repository, UserModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping({"/users", "/users/"})
    public CollectionModel<EntityModel<User>> all() {
        List<EntityModel<User>> users = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                users,
                linkTo(methodOn(UserController.class)
                        .all()
                ).withSelfRel()
        );
    }
    // end::get-aggregate-root[]

    @GetMapping("/users/{id}")
    public EntityModel<User> one(@PathVariable Long id) {

        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return assembler.toModel(user);
    }

    @GetMapping({"/me", "/me/"})
    public EntityModel<User> me() {
        // We assume the User 1 has already authenticated
        User user = repository.findById(1L)
                .orElseThrow(() -> new UserNotFoundException(1L));

        return assembler.toModel(user);
    }
}
