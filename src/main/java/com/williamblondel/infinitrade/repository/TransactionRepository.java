package com.williamblondel.infinitrade.repository;

import com.williamblondel.infinitrade.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}