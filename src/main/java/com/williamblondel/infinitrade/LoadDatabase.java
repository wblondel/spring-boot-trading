package com.williamblondel.infinitrade;

import com.williamblondel.infinitrade.enumeration.TradeTypeEnum;
import com.williamblondel.infinitrade.enumeration.TransactionStatusEnum;
import com.williamblondel.infinitrade.enumeration.TransactionTypeEnum;
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
            TransactionRepository transactionRepository,
            TradeRepository tradeRepository) {
        return args -> {
            // Create user
            User johnDoeUser = new User("john.doe", "john.doe@example.test");
            //User janeDoeUser = new User("jane.doe", "jane.doe@example.test");
            //User davidDoeUser = new User("david.doe", "david.doe@example.test");

            // Save users
            log.info("Preloading {}", userRepository.saveAll(List.of(
                    johnDoeUser/*,
                    janeDoeUser,
                    davidDoeUser*/
            )));

            // Create currencies
            Currency btcCurrency = new Currency(
                    "BTC",
                    "Bitcoin",
                    "Bitcoin is the world’s first cryptocurrency designed to operate decentralized over a " +
                            "blockchain. Unlike traditional currencies, it can be used as a store of value and for " +
                            "making digital payments without a central authority like a bank or a financial " +
                            "institution. The symbol BTC in the market represents Bitcoin.",
                    "https://bitcoin.org"
            );
            Currency ethCurrency = new Currency(
                    "ETH",
                    "Ethereum",
                    "Ethereum (ETH) is a decentralized, open-source blockchain system with smart contract " +
                            "functionality. In terms of market capitalization, it is one of the most prominent " +
                            "cryptocurrencies, second only to Bitcoin.",
                    "https://ethereum.org"
            );
            Currency usdtCurrency = new Currency(
                    "USDT",
                    "Tether",
                    "Tether (USDT) is globally the first and most widely used stablecoin in the crypto " +
                            "market. The USDT price is pegged to the US dollar at a 1 to 1 ratio and is backed by " +
                            "Tether's reserves.",
                    "https://tether.to"
            );
//            Currency usdcCurrency = new Currency(
//                    "USDC",
//                    "USD Coin",
//                    "USD Coin (USDC) is the second most popularly used stablecoin pegged to the US " +
//                            "dollar. It gives all the benefits of cryptocurrency without worrying about high " +
//                            "volatility due to its peg and is issued by regulated financial institutions.",
//                    "https://www.circle.com"
//            );

            // Save currencies
            log.info("Preloading {}", currencyRepository.saveAll(List.of(
                    btcCurrency,
                    ethCurrency,
                    usdtCurrency/*,
                    usdcCurrency*/
            )));

            // Create pairs
            Pair ethUsdtPair = new Pair(ethCurrency, usdtCurrency, "ETHUSDT", 3500.21, 3502.12);
            Pair btcUsdtPair = new Pair(btcCurrency, usdtCurrency, "BTCUSDT", 71012.44, 71012.45);
//            Pair ethBtcPair = new Pair(ethCurrency, btcCurrency, "ETHBTC", 1.0, 1.01);
//            Pair usdcUsdtPair = new Pair(usdcCurrency, usdtCurrency, "USDCUSDT", 0.9991, 0.9992);
//            Pair btcUsdcPair = new Pair(btcCurrency, usdcCurrency, "BTCUSDC", 71012.44, 71012.45);
//            Pair ethUsdcPair = new Pair(ethCurrency, usdcCurrency, "ETHUSDC", 3500.21, 3502.12);

            // Save pairs
            log.info("Preloading {}", pairRepository.saveAll(List.of(
                    ethUsdtPair,
                    btcUsdtPair/*,
                    ethBtcPair,
                    usdcUsdtPair,
                    btcUsdcPair,
                    ethUsdcPair*/
            )));

            // Create wallet
            Wallet johnDoeUsdtWallet = new Wallet(johnDoeUser, usdtCurrency, 50000.0);
//            Wallet johnDoeUsdcWallet = new Wallet(johnDoeUser, usdcCurrency, 25000.0);
            Wallet johnDoeBtcWallet = new Wallet(johnDoeUser, btcCurrency, 0.0);
            Wallet johnDoeEthWallet = new Wallet(johnDoeUser, ethCurrency, (double) 0);

//            Wallet janeDoeUsdtWallet = new Wallet(janeDoeUser, usdtCurrency, 1.0);
//            Wallet janeDoeUsdcWallet = new Wallet(janeDoeUser, usdcCurrency, 2.0);
//            Wallet janeDoeBtcWallet = new Wallet(janeDoeUser, btcCurrency, 0.001);
//            Wallet janeDoeEthWallet = new Wallet(janeDoeUser, ethCurrency, (double) 0);

            // Save wallet
            log.info("Preloading {}", walletRepository.saveAll(List.of(
                    johnDoeUsdtWallet,
//                    johnDoeUsdcWallet,
                    johnDoeBtcWallet,
                    johnDoeEthWallet/*,*/
//                    janeDoeUsdtWallet,
//                    janeDoeUsdcWallet,
//                    janeDoeBtcWallet,
//                    janeDoeEthWallet
            )));

            // Create transactions to match wallets
            Transaction johnDoeUsdtDepositTransaction = new Transaction(johnDoeUsdtWallet, 50000.0, TransactionTypeEnum.DEPOSIT, TransactionStatusEnum.COMPLETED);
//            Transaction johnDoeUsdcDepositTransaction = new Transaction(johnDoeUsdcWallet, 25000.0, TransactionTypeEnum.DEPOSIT, TransactionStatusEnum.COMPLETED);
//            Transaction johnDoeBtcDepositTransaction = new Transaction(johnDoeBtcWallet, 0.0033, TransactionTypeEnum.DEPOSIT, TransactionStatusEnum.COMPLETED);
//            Transaction janeDoeUsdtDepositTransaction = new Transaction(janeDoeUsdtWallet, 1.0, TransactionTypeEnum.DEPOSIT, TransactionStatusEnum.COMPLETED);
//            Transaction janeDoeUsdcDepositTransaction = new Transaction(janeDoeUsdcWallet, 2.0, TransactionTypeEnum.DEPOSIT, TransactionStatusEnum.COMPLETED);
//            Transaction janeDoeBtcDepositTransaction = new Transaction(janeDoeBtcWallet, 0.001, TransactionTypeEnum.DEPOSIT, TransactionStatusEnum.COMPLETED);

            // Save transactions
            log.info("Preloading {}", transactionRepository.saveAll(List.of(
                    johnDoeUsdtDepositTransaction/*,*/
//                    johnDoeUsdcDepositTransaction,
//                    johnDoeBtcDepositTransaction,
//                    janeDoeUsdtDepositTransaction,
//                    janeDoeUsdcDepositTransaction,
//                    janeDoeBtcDepositTransaction
            )));

            // Create trades
            Trade johnDoeTrade1 = new Trade(johnDoeUser, ethUsdtPair, TradeTypeEnum.SELL, 0.2, ethUsdtPair.getBidPrice());
            Trade johnDoeTrade2 = new Trade(johnDoeUser, btcUsdtPair, TradeTypeEnum.BUY, 0.4, btcUsdtPair.getAskPrice());

//            Trade janeDoeTrade1 = new Trade(janeDoeUser, btcUsdtPair, TradeTypeEnum.SELL, 0.123, btcUsdtPair.getBidPrice());
//            Trade janeDoeTrade2 = new Trade(janeDoeUser, btcUsdtPair, TradeTypeEnum.BUY, 0.234, btcUsdtPair.getAskPrice());

            // Save trades
            log.info("Preloading {}", tradeRepository.saveAll(List.of(
                    johnDoeTrade1,
                    johnDoeTrade2/*,*/
//                    janeDoeTrade1,
//                    janeDoeTrade2
            )));
        };
    }
}
