package com.fayyaad.transactionapi.repository;

import com.fayyaad.transactionapi.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account, Long> {
}