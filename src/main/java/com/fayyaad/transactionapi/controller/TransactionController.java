package com.fayyaad.transactionapi.controller;
import com.fayyaad.transactionapi.model.Account;
import com.fayyaad.transactionapi.model.Transaction;
import com.fayyaad.transactionapi.service.TransactionService;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionOps;

    public TransactionController(TransactionService transactionService) {
        this.transactionOps = transactionService;
    }

    @PostMapping("/deposit/{accountId}")
    public Account deposit(@PathVariable Long accountId,
                           @RequestParam BigDecimal amount) {

        return transactionOps.deposit(accountId, amount);
    }

    @PostMapping("/withdraw/{accountId}")
    public Account withdraw(@PathVariable Long accountId,
                            @RequestParam BigDecimal amount) {

        return transactionOps.withdraw(accountId, amount);
    }

    @PostMapping("/transfer")
    public void transfer(@RequestParam Long fromAccountId,
                         @RequestParam Long toAccountId,
                         @RequestParam BigDecimal amount) {

        transactionOps.transfer(fromAccountId, toAccountId, amount);
    }

    @GetMapping("/{accountId}")
    public List<Transaction> history(@PathVariable Long accountId) {

        return transactionOps.getTransactionHistory(accountId);
    }
}