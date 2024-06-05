package com.williamblondel.infinitrade.repository;

import com.williamblondel.infinitrade.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<Trade, Long> {

}