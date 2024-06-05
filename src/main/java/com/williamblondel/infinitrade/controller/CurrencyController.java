package com.williamblondel.infinitrade.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.williamblondel.infinitrade.assembler.CurrencyModelAssembler;
import com.williamblondel.infinitrade.exception.CurrencyNotFoundException;
import com.williamblondel.infinitrade.model.Currency;
import com.williamblondel.infinitrade.repository.CurrencyRepository;
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
public class CurrencyController {
    private final CurrencyRepository repository;

    private final CurrencyModelAssembler assembler;

    CurrencyController(CurrencyRepository repository, CurrencyModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping({"/cryptocurrencies", "/cryptocurrencies/"})
    public CollectionModel<EntityModel<Currency>> all() {
        List<EntityModel<Currency>> cryptocurrencies = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(cryptocurrencies, linkTo(methodOn(CurrencyController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping({"/cryptocurrencies", "/cryptocurrencies/"})
    ResponseEntity<?> newCryptocurrency(@RequestBody Currency newCurrency) {
        EntityModel<Currency> entityModel = assembler.toModel(repository.save(newCurrency));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/cryptocurrencies/{id}")
    public EntityModel<Currency> one(@PathVariable Long id) {

        Currency currency = repository.findById(id)
                .orElseThrow(() -> new CurrencyNotFoundException(id));

        return assembler.toModel(currency);
    }

    @PutMapping("/cryptocurrencies/{id}")
    ResponseEntity<?> replaceCryptocurrency(@PathVariable Long id, @RequestBody Currency newCurrency) {
        Currency updatedCurrency = repository.findById(id)
                .map(cryptocurrency -> {
                    cryptocurrency.setTicker(newCurrency.getTicker());
                    cryptocurrency.setName(newCurrency.getName());
                    cryptocurrency.setDescription(newCurrency.getDescription());
                    cryptocurrency.setWebsite(newCurrency.getWebsite());

                    return repository.save(cryptocurrency);
                })
                .orElseGet(() -> {
                    newCurrency.setId(id);
                    return repository.save(newCurrency);
                });

        EntityModel<Currency> entityModel = assembler.toModel(updatedCurrency);

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
