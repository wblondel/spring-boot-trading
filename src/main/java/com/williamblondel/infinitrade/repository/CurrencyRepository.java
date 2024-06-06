package com.williamblondel.infinitrade.repository;

import com.williamblondel.infinitrade.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Optional<Currency> findFirstByTicker(String ticker);

    boolean existsByTicker(String ticker);

    void deleteByTicker(String ticker);
}