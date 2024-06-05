package com.williamblondel.infinitrade.assembler;

import com.williamblondel.infinitrade.controller.UserWalletController;
import com.williamblondel.infinitrade.controller.WalletController;
import com.williamblondel.infinitrade.model.Wallet;
import com.williamblondel.infinitrade.assembler.CustomRepresentationModelAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class WalletModelAssembler implements CustomRepresentationModelAssembler<Wallet, EntityModel<Wallet>> {

    @Override
    public EntityModel<Wallet> toModel(Wallet wallet) {
        return toModel(wallet, true);
    }

    @Override
    public EntityModel<Wallet> toModel(Wallet wallet, Boolean includeAll) {
        EntityModel<Wallet> entityModel = EntityModel.of(wallet,
                linkTo(methodOn(WalletController.class).one(wallet.getId())).withSelfRel());

        if (includeAll) {
            entityModel.add(linkTo(methodOn(WalletController.class).all()).withRel("wallets"));
        }

        return entityModel;
    }
}
