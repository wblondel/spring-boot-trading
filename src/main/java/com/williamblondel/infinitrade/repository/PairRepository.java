package com.williamblondel.infinitrade.repository;

import com.williamblondel.infinitrade.model.Pair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PairRepository extends JpaRepository<Pair, Long> {
    Optional<Pair> findByPairCode(String pairCode);
}