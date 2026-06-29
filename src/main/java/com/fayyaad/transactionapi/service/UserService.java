package com.fayyaad.transactionapi.service;

import com.fayyaad.transactionapi.model.User;
import com.fayyaad.transactionapi.repository.UserRepo;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userDatabase;

    public UserService(UserRepo userRepo) {
        this.userDatabase = userRepo;
    }

    public User createUser(User user) {
        if (user.getFullName() == null || user.getFullName().isBlank()) {
            throw new IllegalArgumentException("* Name is Required");
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("* Email is Required");
        }

        return userDatabase.save(user);
    }
}