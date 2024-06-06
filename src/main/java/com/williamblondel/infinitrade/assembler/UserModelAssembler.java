package com.williamblondel.infinitrade.assembler;

import com.williamblondel.infinitrade.controller.UserController;
import com.williamblondel.infinitrade.controller.UserTradeController;
import com.williamblondel.infinitrade.controller.UserWalletController;
import com.williamblondel.infinitrade.model.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {

    @Override
    public EntityModel<User> toModel(User user) {

        return EntityModel.of(
                user,
                linkTo(methodOn(UserController.class)
                        .one(user.getId())
                ).withSelfRel(),
                linkTo(methodOn(UserWalletController.class)
                        .all(user.getId())
                ).withRel("user-wallets"),
                linkTo(methodOn(UserTradeController.class)
                        .all(user.getId())
                ).withRel("user-trades"),
                linkTo(methodOn(UserController.class)
                        .all()
                ).withRel("users")
        );
    }
}
