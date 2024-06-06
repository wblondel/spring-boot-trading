package com.williamblondel.infinitrade.assembler;

import com.williamblondel.infinitrade.controller.PairController;
import com.williamblondel.infinitrade.controller.PairTradeController;
import com.williamblondel.infinitrade.model.Pair;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PairModelAssembler implements RepresentationModelAssembler<Pair, EntityModel<Pair>> {

    @Override
    public EntityModel<Pair> toModel(Pair pair) {

        return EntityModel.of(
                pair,
                linkTo(methodOn(PairController.class)
                        .one(pair.getPairCode())
                ).withSelfRel(),
                linkTo(methodOn(PairTradeController.class)
                        .all(pair.getPairCode())
                ).withRel("pair-trades"),
                linkTo(methodOn(PairController.class)
                        .all()
                ).withRel("pairs"));
    }
}
