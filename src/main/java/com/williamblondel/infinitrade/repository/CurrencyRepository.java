package com.williamblondel.infinitrade.repository;

import com.williamblondel.infinitrade.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

}