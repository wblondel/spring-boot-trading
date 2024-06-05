package com.williamblondel.infinitrade;

import com.williamblondel.infinitrade.model.Cryptocurrency;
import com.williamblondel.infinitrade.repository.CryptocurrencyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(CryptocurrencyRepository repository) {
        return args -> {
            log.info("Preloading {}", repository.save(new Cryptocurrency("BTC", "Bitcoin", "Bitcoin is the world’s first cryptocurrency designed to operate decentralized over a blockchain. Unlike traditional currencies, it can be used as a store of value and for making digital payments without a central authority like a bank or a financial institution. The symbol BTC in the market represents Bitcoin.", "https://bitcoin.org")));
            log.info("Preloading {}", repository.save(new Cryptocurrency("ETH", "Ethereum", "Ethereum (ETH) is a decentralized, open-source blockchain system with smart contract functionality. In terms of market capitalization, it is one of the most prominent cryptocurrencies, second only to Bitcoin.", "https://ethereum.org")));
            log.info("Preloading {}", repository.save(new Cryptocurrency("USDT", "Tether", "Tether (USDT) is globally the first and most widely used stablecoin in the crypto market. The USDT price is pegged to the US dollar at a 1 to 1 ratio and is backed by Tether's reserves.", "https://tether.to")));
        };
    }
}
