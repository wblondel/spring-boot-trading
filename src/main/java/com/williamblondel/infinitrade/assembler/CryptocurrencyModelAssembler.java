package com.williamblondel.infinitrade.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.williamblondel.infinitrade.controller.CryptocurrencyController;
import com.williamblondel.infinitrade.model.Cryptocurrency;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class CryptocurrencyModelAssembler implements RepresentationModelAssembler<Cryptocurrency, EntityModel<Cryptocurrency>> {

    @Override
    public EntityModel<Cryptocurrency> toModel(Cryptocurrency cryptocurrency) {

        return EntityModel.of(cryptocurrency,
                linkTo(methodOn(CryptocurrencyController.class).one(cryptocurrency.getId())).withSelfRel(),
                linkTo(methodOn(CryptocurrencyController.class).all()).withRel("cryptocurrencies"));
    }
}
