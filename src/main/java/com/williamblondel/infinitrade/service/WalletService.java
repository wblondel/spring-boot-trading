package com.williamblondel.infinitrade.service;

import com.williamblondel.infinitrade.exception.WalletNotFoundException;
import com.williamblondel.infinitrade.model.Currency;
import com.williamblondel.infinitrade.model.User;
import com.williamblondel.infinitrade.model.Wallet;
import com.williamblondel.infinitrade.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Optional<Wallet> findFirstUserWalletForCurrency(User user, Currency currency) {
        return walletRepository.findFirstByUser_IdAndCurrency_Id(user.getId(), currency.getId());
    }

    public Wallet findFirstUserWalletForCurrencyOrFail(User user, Currency currency) {
        return walletRepository.findFirstByUser_IdAndCurrency_Id(user.getId(), currency.getId())
                .orElseThrow(() -> new WalletNotFoundException(user, currency));
    }

    public Boolean hasSufficientBalance(Wallet wallet, Double amount) {
        return wallet.getBalance() >= amount;
    }

    @Transactional
    public void deductFromWallet(Wallet wallet, Double amount) {
        wallet.setBalance(wallet.getBalance() - amount);
        wallet.setUpdatedAt(LocalDateTime.now());
        walletRepository.save(wallet);
    }

    @Transactional
    public void creditToWallet(Wallet wallet, Double amount) {
        wallet.setBalance(wallet.getBalance() + amount);
        wallet.setUpdatedAt(LocalDateTime.now());
        walletRepository.save(wallet);
    }
}
