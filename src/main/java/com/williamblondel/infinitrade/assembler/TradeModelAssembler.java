package com.williamblondel.infinitrade.assembler;

import com.williamblondel.infinitrade.controller.PairController;
import com.williamblondel.infinitrade.controller.TradeController;
import com.williamblondel.infinitrade.model.Trade;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TradeModelAssembler implements RepresentationModelAssembler<Trade, EntityModel<Trade>> {

    @Override
    public EntityModel<Trade> toModel(Trade trade) {

        return EntityModel.of(
                trade,
                linkTo(methodOn(TradeController.class)
                        .one(trade.getId())
                ).withSelfRel(),
                linkTo(methodOn(PairController.class)
                        .one(trade.getPair().getPairCode())
                ).withRel("pair"),
                linkTo(methodOn(TradeController.class)
                        .all()
                ).withRel("trades"));
    }
}
