package com.williamblondel.infinitrade.controller;

import com.williamblondel.infinitrade.assembler.CurrencyModelAssembler;
import com.williamblondel.infinitrade.exception.CurrencyNotFoundException;
import com.williamblondel.infinitrade.model.Currency;
import com.williamblondel.infinitrade.repository.CurrencyRepository;
import com.williamblondel.infinitrade.requests.CreateCurrencyRequest;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
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
    ResponseEntity<?> newCryptocurrency(@Valid @RequestBody CreateCurrencyRequest createCurrencyRequest) {
        Currency currencyToProcess = createCurrencyRequest.toCurrency();

        if (repository.existsByTicker(currencyToProcess.getTicker())) {
            throw new EntityExistsException("Currency " + currencyToProcess.getTicker() + " already exists");
        }

        Currency savedCurrency = repository.save(currencyToProcess);
        EntityModel<Currency> entityModel = assembler.toModel(savedCurrency);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/currencies/{ticker}")
    public EntityModel<Currency> one(@PathVariable String ticker) {

        Currency currency = repository.findFirstByTicker(ticker)
                .orElseThrow(() -> new CurrencyNotFoundException(ticker));

        return assembler.toModel(currency);
    }
}
