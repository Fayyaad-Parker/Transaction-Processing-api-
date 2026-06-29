package com.fayyaad.transactionapi.repository;

import com.fayyaad.transactionapi.model.Account;
import com.fayyaad.transactionapi.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccount(Account account);
}