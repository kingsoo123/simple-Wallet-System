package com.example.test.repo;

import com.example.test.model.Account;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account, Long> {

    boolean existsByAccountNumber(BigInteger account);

    Account findByAccountNumber(BigInteger toAccount);
}
