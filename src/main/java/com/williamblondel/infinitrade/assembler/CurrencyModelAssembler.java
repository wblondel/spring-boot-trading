package com.williamblondel.infinitrade.assembler;

import com.williamblondel.infinitrade.controller.CurrencyController;
import com.williamblondel.infinitrade.model.Currency;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CurrencyModelAssembler implements RepresentationModelAssembler<Currency, EntityModel<Currency>> {

    @Override
    public EntityModel<Currency> toModel(Currency currency) {

        return EntityModel.of(
                currency,
                linkTo(methodOn(CurrencyController.class)
                        .one(currency.getId())
                ).withSelfRel(),
                linkTo(methodOn(CurrencyController.class)
                        .all()
                ).withRel("currencies")
        );
    }
}
