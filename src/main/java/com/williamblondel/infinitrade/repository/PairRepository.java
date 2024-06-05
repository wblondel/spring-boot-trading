package com.williamblondel.infinitrade.repository;

import com.williamblondel.infinitrade.model.Pair;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PairRepository extends JpaRepository<Pair, Long> {

}