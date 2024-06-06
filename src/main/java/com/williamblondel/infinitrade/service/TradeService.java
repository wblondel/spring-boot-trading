package com.williamblondel.infinitrade.service;

import com.williamblondel.infinitrade.enumeration.TradeTypeEnum;
import com.williamblondel.infinitrade.exception.InsufficientBalanceException;
import com.williamblondel.infinitrade.model.Pair;
import com.williamblondel.infinitrade.model.Trade;
import com.williamblondel.infinitrade.model.User;
import com.williamblondel.infinitrade.model.Wallet;
import com.williamblondel.infinitrade.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TradeService {
    private final TradeRepository tradeRepository;
    private final WalletService walletService;

    @Value("${trading.fee.in.percent}")
    private double TRADING_FEE;

    @Autowired
    public TradeService(TradeRepository tradeRepository, WalletService walletService) {
        this.tradeRepository = tradeRepository;
        this.walletService = walletService;
    }

    @Transactional
    public Trade create(Trade trade) {
        TradeTypeEnum tradeType = trade.getTradeType();
        Pair tradePair = trade.getPair();
        User tradeUser = trade.getUser();

        Wallet walletQuoteCurrency = walletService.findFirstUserWalletForCurrencyOrFail(tradeUser, tradePair.getQuoteCurrency());
        Wallet walletBaseCurrency = walletService.findFirstUserWalletForCurrencyOrFail(tradeUser, tradePair.getBaseCurrency());

        Double price = determinePrice(tradeType, tradePair);
        Double totalInQuoteCurrency = trade.getAmount() * price;

        if (tradeType == TradeTypeEnum.BUY) {
            checkSufficientBalance(walletQuoteCurrency, totalInQuoteCurrency);
            walletService.deductFromWallet(walletQuoteCurrency, totalInQuoteCurrency);
            walletService.creditToWallet(walletBaseCurrency, trade.getAmount());
        } else {
            checkSufficientBalance(walletBaseCurrency, trade.getAmount());
            walletService.deductFromWallet(walletBaseCurrency, trade.getAmount());
            walletService.creditToWallet(walletQuoteCurrency, totalInQuoteCurrency);
        }

        trade.setPrice(price);
        trade.setCreatedAt(LocalDateTime.now());

        return tradeRepository.save(trade);
    }

    private void checkSufficientBalance(Wallet wallet, Double amount) {
        if (!walletService.hasSufficientBalance(wallet, amount)) {
            throw new InsufficientBalanceException(wallet);
        }
    }

    private Double determinePrice(TradeTypeEnum tradeType, Pair tradePair) {
        return tradeType == TradeTypeEnum.BUY ? tradePair.getAskPrice() : tradePair.getBidPrice();
    }
}
