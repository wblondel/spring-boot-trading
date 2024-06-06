package com.williamblondel.infinitrade;

import com.williamblondel.infinitrade.model.*;
import com.williamblondel.infinitrade.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(
            UserRepository userRepository,
            CurrencyRepository currencyRepository,
            PairRepository pairRepository,
            WalletRepository walletRepository,
            TransactionRepository transactionRepository, TradeRepository tradeRepository) {
        return args -> {
            // Create user
            User johnDoeUser = new User("john.doe", "john.doe@example.test", "aaa");
            User janeDoeUser = new User("jane.doe", "jane.doe@example.test", "bbb");
            User davidDoeUser = new User("david.doe", "david.doe@example.test", "bbb");

            // Save users
            log.info("Preloading {}", userRepository.saveAll(List.of(johnDoeUser, janeDoeUser, davidDoeUser)));

            // Create currencies
            Currency btcCurrency = new Currency("BTC", "Bitcoin", "Bitcoin is the world’s first cryptocurrency designed to operate decentralized over a blockchain. Unlike traditional currencies, it can be used as a store of value and for making digital payments without a central authority like a bank or a financial institution. The symbol BTC in the market represents Bitcoin.", "https://bitcoin.org");
            Currency ethCurrency = new Currency("ETH", "Ethereum", "Ethereum (ETH) is a decentralized, open-source blockchain system with smart contract functionality. In terms of market capitalization, it is one of the most prominent cryptocurrencies, second only to Bitcoin.", "https://ethereum.org");
            Currency usdtCurrency = new Currency("USDT", "Tether", "Tether (USDT) is globally the first and most widely used stablecoin in the crypto market. The USDT price is pegged to the US dollar at a 1 to 1 ratio and is backed by Tether's reserves.", "https://tether.to");
            Currency usdcCurrency = new Currency("USDC", "USD Coin", "USD Coin (USDC) is the second most popularly used stablecoin pegged to the US dollar. It gives all the benefits of cryptocurrency without worrying about high volatility due to its peg and is issued by regulated financial institutions.", "https://www.circle.com");

            // Save currencies
            log.info("Preloading {}", currencyRepository.saveAll(List.of(btcCurrency, ethCurrency, usdtCurrency, usdcCurrency)));

            // Create pairs
            Pair ethUsdtPair = new Pair(ethCurrency, usdtCurrency);
            Pair btcUsdtPair = new Pair(btcCurrency, usdtCurrency);
            Pair ethBtcPair = new Pair(ethCurrency, btcCurrency);
            Pair usdcUsdtPair = new Pair(usdcCurrency, usdtCurrency);
            Pair btcUsdcPair = new Pair(btcCurrency, usdcCurrency);
            Pair ethUsdcPair = new Pair(ethCurrency, usdcCurrency);

            // Save pairs
            log.info("Preloading {}", pairRepository.saveAll(List.of(
                    ethUsdtPair,
                    btcUsdtPair,
                    ethBtcPair,
                    usdcUsdtPair,
                    btcUsdcPair,
                    ethUsdcPair
            )));

            // Create wallet
            Wallet johnDoeUsdtWallet = new Wallet(johnDoeUser, usdtCurrency, 50000.0);
            Wallet johnDoeUsdcWallet = new Wallet(johnDoeUser, usdcCurrency, 25000.0);
            Wallet johnDoeBtcWallet = new Wallet(johnDoeUser, btcCurrency, 0.0033);
            Wallet janeDoeUsdtWallet = new Wallet(janeDoeUser, usdtCurrency, 1.0);
            Wallet janeDoeUsdcWallet = new Wallet(janeDoeUser, usdcCurrency, 2.0);
            Wallet janeDoeBtcWallet = new Wallet(janeDoeUser, btcCurrency, 0.001);

            // Save wallet
            log.info("Preloading {}", walletRepository.saveAll(List.of(
                    johnDoeUsdtWallet,
                    johnDoeUsdcWallet,
                    johnDoeBtcWallet,
                    janeDoeUsdtWallet,
                    janeDoeUsdcWallet,
                    janeDoeBtcWallet
            )));

            // Create transactions to match wallets
            Transaction johnDoeUsdtDepositTransaction = new Transaction(johnDoeUsdtWallet, 50000.0, "deposit", "completed");
            Transaction johnDoeUsdcDepositTransaction = new Transaction(johnDoeUsdcWallet, 25000.0, "deposit", "completed");
            Transaction johnDoeBtcDepositTransaction = new Transaction(johnDoeBtcWallet, 0.0033, "deposit", "completed");
            Transaction janeDoeUsdtDepositTransaction = new Transaction(janeDoeUsdtWallet, 1.0, "deposit", "completed");
            Transaction janeDoeUsdcDepositTransaction = new Transaction(janeDoeUsdcWallet, 2.0, "deposit", "completed");
            Transaction janeDoeBtcDepositTransaction = new Transaction(janeDoeBtcWallet, 0.001, "deposit", "completed");

            // Save transactions
            log.info("Preloading {}", transactionRepository.saveAll(List.of(
                    johnDoeUsdtDepositTransaction,
                    johnDoeUsdcDepositTransaction,
                    johnDoeBtcDepositTransaction,
                    janeDoeUsdtDepositTransaction,
                    janeDoeUsdcDepositTransaction,
                    janeDoeBtcDepositTransaction
            )));

            // Create trades
            Trade johnDoeTrade1 = new Trade(johnDoeUser, ethUsdtPair, "sell", 1.0, 1.0, 0.2);
            Trade johnDoeTrade2 = new Trade(johnDoeUser, btcUsdtPair, "buy", 1.0, 1.0, 0.2);

            Trade janeDoeTrade1 = new Trade(janeDoeUser, btcUsdtPair, "sell", 1.0, 1.0, 0.2);
            Trade janeDoeTrade2 = new Trade(janeDoeUser, btcUsdtPair, "buy", 1.0, 1.0, 0.2);

            // Save trades
            log.info("Preloading {}", tradeRepository.saveAll(List.of(
                    johnDoeTrade1,
                    johnDoeTrade2,
                    janeDoeTrade1,
                    janeDoeTrade2
            )));
        };
    }
}
