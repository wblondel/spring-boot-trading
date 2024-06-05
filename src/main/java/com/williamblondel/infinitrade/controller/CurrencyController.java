package com.williamblondel.infinitrade.controller;

import com.williamblondel.infinitrade.assembler.CurrencyModelAssembler;
import com.williamblondel.infinitrade.exception.CurrencyNotFoundException;
import com.williamblondel.infinitrade.model.Currency;
import com.williamblondel.infinitrade.repository.CurrencyRepository;
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
public class CurrencyController {
    private final CurrencyRepository repository;

    private final CurrencyModelAssembler assembler;

    CurrencyController(CurrencyRepository repository, CurrencyModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping({"/currencies", "/currencies/"})
    public CollectionModel<EntityModel<Currency>> all() {
        List<EntityModel<Currency>> currencies = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                currencies,
                linkTo(methodOn(CurrencyController.class)
                        .all()
                ).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping({"/currencies", "/currencies/"})
    ResponseEntity<?> newCryptocurrency(@RequestBody Currency newCurrency) {
        EntityModel<Currency> entityModel = assembler.toModel(repository.save(newCurrency));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/currencies/{id}")
    public EntityModel<Currency> one(@PathVariable Long id) {

        Currency currency = repository.findById(id)
                .orElseThrow(() -> new CurrencyNotFoundException(id));

        return assembler.toModel(currency);
    }

    @PutMapping("/currencies/{id}")
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

    @DeleteMapping("/currencies/{id}")
    ResponseEntity<?> deleteCryptocurrency(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
