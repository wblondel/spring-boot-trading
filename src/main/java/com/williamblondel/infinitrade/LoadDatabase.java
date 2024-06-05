package com.williamblondel.infinitrade;

import com.williamblondel.infinitrade.model.Currency;
import com.williamblondel.infinitrade.model.Pair;
import com.williamblondel.infinitrade.model.User;
import com.williamblondel.infinitrade.model.Wallet;
import com.williamblondel.infinitrade.repository.CurrencyRepository;
import com.williamblondel.infinitrade.repository.PairRepository;
import com.williamblondel.infinitrade.repository.UserRepository;
import com.williamblondel.infinitrade.repository.WalletRepository;
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
            WalletRepository walletRepository
    ) {
        return args -> {
            // Create user
            User johnDoeUser = new User("john.doe", "john.doe@example.test", "aaa");
            User janeDoeUser = new User("jane.doe", "jane.doe@example.test", "bbb");

            // Save users
            log.info("Preloading {}", userRepository.saveAll(List.of(johnDoeUser, janeDoeUser)));

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

            // Save wallet
            log.info("Preloading {}", walletRepository.save(johnDoeUsdtWallet));
        };
    }
}
