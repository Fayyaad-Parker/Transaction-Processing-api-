package com.fayyaad.transactionapi.service;

import com.fayyaad.transactionapi.model.Account;
import com.fayyaad.transactionapi.model.Transaction;
import com.fayyaad.transactionapi.model.TransactionType;
import com.fayyaad.transactionapi.repository.AccountRepo;
import com.fayyaad.transactionapi.repository.TransactionRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

    private final AccountRepo accountDatabase;
    private final TransactionRepo transactionDatabase;

    public TransactionService(AccountRepo accountRepo,TransactionRepo transactionRepo) {

        this.accountDatabase = accountRepo;
        this.transactionDatabase = transactionRepo;
    }
// this means that all transactions have to succeed together else it rollsback, protects losses.
    @Transactional
    public Account deposit(Long accountId, BigDecimal amount) {

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        Account account = accountDatabase.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        account.setBalance(account.getBalance().add(amount));

        createTransaction(account, amount, TransactionType.DEPOSIT);

        return accountDatabase.save(account);
    }

    @Transactional
    public Account withdraw(Long accountId, BigDecimal amount) {

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        Account account = accountDatabase.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        account.setBalance(account.getBalance().subtract(amount));

        createTransaction(account, amount, TransactionType.WITHDRAWAL);

        return accountDatabase.save(account);
    }

    @Transactional
    public void transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        if (fromAccountId.equals(toAccountId)) {
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }

        Account fromAccount = accountDatabase.findById(fromAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Source account not found"));

        Account toAccount = accountDatabase.findById(toAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Destination account not found"));

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        createTransaction(fromAccount, amount, TransactionType.TRANSFER_OUT);
        createTransaction(toAccount, amount, TransactionType.TRANSFER_IN);

        accountDatabase.save(fromAccount);
        accountDatabase.save(toAccount);
    }

    public List<Transaction> getTransactionHistory(Long accountId) {

        Account account = accountDatabase.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        return transactionDatabase.findByAccount(account);
    }

    private void createTransaction(Account account, BigDecimal amount, TransactionType type) {

        Transaction transaction = new Transaction();

        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setType(type);

        transactionDatabase.save(transaction);
    }
}