package com.fayyaad.transactionapi.controller;
import com.fayyaad.transactionapi.model.Account;
import com.fayyaad.transactionapi.service.AccountService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountOps;

    public AccountController(AccountService accountService) {
        this.accountOps = accountService;
    }

    @PostMapping("/user/{userId}")
    public Account createAccount(@PathVariable Long userId) {
        return accountOps.createAccount(userId);
    }
}