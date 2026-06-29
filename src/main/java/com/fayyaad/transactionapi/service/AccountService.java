package com.fayyaad.transactionapi.service;

import com.fayyaad.transactionapi.model.Account;
import com.fayyaad.transactionapi.model.User;
import com.fayyaad.transactionapi.repository.AccountRepo;
import com.fayyaad.transactionapi.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepo accountDatabase;
    private final UserRepo userDatabase;

    public AccountService(AccountRepo accountRepo, UserRepo userRepo) {
        this.accountDatabase = accountRepo;
        this.userDatabase = userRepo;
    }

    public Account createAccount(Long userId) {

        User user = userDatabase.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Account account = new Account();

        account.setUser(user);

        account.setBalance(BigDecimal.ZERO);

        account.setAccountNumber(
                // Randomly selects unique random numbers to make an 8 digit ID
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase()
        );

        return accountDatabase.save(account);
    }
}