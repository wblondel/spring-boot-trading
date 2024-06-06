package com.williamblondel.infinitrade.exception;

import com.williamblondel.infinitrade.model.Currency;
import com.williamblondel.infinitrade.model.User;

public class WalletNotFoundException extends RuntimeException {
    public WalletNotFoundException(Long id) {
        super("Could not find wallet with id " + id);
    }

    public WalletNotFoundException(User user, Currency currency) {
        super("Could not find wallet for user ID " + user.getId() + " in currency " + currency.getTicker() + " (ID " + currency.getId() + ")");
    }
}
