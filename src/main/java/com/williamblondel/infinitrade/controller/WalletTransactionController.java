package com.williamblondel.infinitrade.controller;

import com.williamblondel.infinitrade.assembler.TransactionModelAssembler;
import com.williamblondel.infinitrade.exception.WalletNotFoundException;
import com.williamblondel.infinitrade.model.Transaction;
import com.williamblondel.infinitrade.model.Wallet;
import com.williamblondel.infinitrade.repository.WalletRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class WalletTransactionController {
    private final WalletRepository walletRepository;
    private final TransactionModelAssembler transactionAssembler;

    public WalletTransactionController(WalletRepository walletRepository, TransactionModelAssembler transactionAssembler) {
        this.walletRepository = walletRepository;
        this.transactionAssembler = transactionAssembler;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping({"/wallets/{walletId}/transactions", "/wallets/{walletId}/transactions"})
    public CollectionModel<EntityModel<Transaction>> all(@PathVariable Long walletId) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId));

        List<EntityModel<Transaction>> transactions = wallet.getTransactions().stream()
                .map(transactionAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                transactions,
                linkTo(methodOn(WalletTransactionController.class)
                        .all(walletId)
                ).withSelfRel()
        );
    }
    // end::get-aggregate-root[]

}
