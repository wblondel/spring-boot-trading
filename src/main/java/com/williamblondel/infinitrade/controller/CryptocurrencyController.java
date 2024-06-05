package com.williamblondel.infinitrade.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.williamblondel.infinitrade.assembler.CryptocurrencyModelAssembler;
import com.williamblondel.infinitrade.exception.CryptocurrencyNotFoundException;
import com.williamblondel.infinitrade.model.Cryptocurrency;
import com.williamblondel.infinitrade.repository.CryptocurrencyRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class CryptocurrencyController {
    private final CryptocurrencyRepository repository;

    private final CryptocurrencyModelAssembler assembler;

    CryptocurrencyController(CryptocurrencyRepository repository, CryptocurrencyModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping({"/cryptocurrencies", "/cryptocurrencies/"})
    public CollectionModel<EntityModel<Cryptocurrency>> all() {
        List<EntityModel<Cryptocurrency>> cryptocurrencies = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(cryptocurrencies, linkTo(methodOn(CryptocurrencyController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping({"/cryptocurrencies", "/cryptocurrencies/"})
    ResponseEntity<?> newCryptocurrency(@RequestBody Cryptocurrency newCryptocurrency) {
        EntityModel<Cryptocurrency> entityModel = assembler.toModel(repository.save(newCryptocurrency));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/cryptocurrencies/{id}")
    public EntityModel<Cryptocurrency> one(@PathVariable Long id) {

        Cryptocurrency cryptocurrency = repository.findById(id)
                .orElseThrow(() -> new CryptocurrencyNotFoundException(id));

        return assembler.toModel(cryptocurrency);
    }

    @PutMapping("/cryptocurrencies/{id}")
    ResponseEntity<?> replaceCryptocurrency(@PathVariable Long id, @RequestBody Cryptocurrency newCryptocurrency) {
        Cryptocurrency updatedCryptocurrency = repository.findById(id)
                .map(cryptocurrency -> {
                    cryptocurrency.setTicker(newCryptocurrency.getTicker());
                    cryptocurrency.setName(newCryptocurrency.getName());
                    cryptocurrency.setDescription(newCryptocurrency.getDescription());
                    cryptocurrency.setWebsite(newCryptocurrency.getWebsite());

                    return repository.save(cryptocurrency);
                })
                .orElseGet(() -> {
                    newCryptocurrency.setId(id);
                    return repository.save(newCryptocurrency);
                });

        EntityModel<Cryptocurrency> entityModel = assembler.toModel(updatedCryptocurrency);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/cryptocurrencies/{id}")
    ResponseEntity<?> deleteCryptocurrency(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
