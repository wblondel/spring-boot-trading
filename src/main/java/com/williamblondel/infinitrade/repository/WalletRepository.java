package com.williamblondel.infinitrade.repository;

import com.williamblondel.infinitrade.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

}