package com.williamblondel.infinitrade.repository;

import com.williamblondel.infinitrade.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findFirstByUser_IdAndCurrency_Id(Long userId, Long currencyId);
}