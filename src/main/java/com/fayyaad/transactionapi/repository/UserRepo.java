package com.fayyaad.transactionapi.repository;

import com.fayyaad.transactionapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}