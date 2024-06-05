package com.williamblondel.infinitrade.repository;

import com.williamblondel.infinitrade.model.Cryptocurrency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptocurrencyRepository extends JpaRepository<Cryptocurrency, Long> {

}