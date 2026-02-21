package com.example.test.service;

import com.example.test.dto.DoDepositDTO;
import com.example.test.dto.DoTransDto;
import com.example.test.dto.UserRequestDTO;
import com.example.test.exception.AlreadyExistsException;
import com.example.test.model.Account;
import com.example.test.model.Users;
import com.example.test.model.WalletBalance;
import com.example.test.repo.AccountRepo;
import com.example.test.repo.UserRepo;
import com.example.test.repo.WalletBalanceRepo;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DoService implements ServiceCall {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private WalletBalanceRepo walletBalanceRepo;

    @Override
    public Users createUserAndAccount(UserRequestDTO request) {

        if (existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException("Email already exists");
        }

        String phone = request.getPhoneNumber();

        BigInteger accNum = null;

        if (phone != null && phone.length() > 1) {
            accNum = new BigInteger(phone.substring(1));
        }

        WalletBalance balance = WalletBalance.builder()
                .amount(BigDecimal.ZERO)
                .build();

        walletBalanceRepo.save(balance);

        Account account = Account.builder()
                .accountNumber(accNum)
                .balance(balance)
                .build();

        accountRepo.save(account);

        Users userInfo = Users.builder()
                .email(request.getEmail())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .phoneNumber(request.getPhoneNumber())
                .account(account)
                .build();

        userRepo.save(userInfo);

        return userInfo;
    }

    private boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    public Account doIntraTransfer(DoTransDto request) {

        String accountNumber = request.getToAccount();
        String fromAccount = request.getFromAccount();

        BigInteger fromAccNum = null;

        BigInteger accNum = null;

        if (fromAccount != null && fromAccount.length() > 1) {
            fromAccNum = new BigInteger(fromAccount);
        }

        if (accountNumber != null && accountNumber.length() > 1) {
            accNum = new BigInteger(accountNumber);
        }

        if (!existsByAccount(accNum)) {
            throw new RuntimeException("Destination account does not exist");
        }

        Account fromAcc = accountRepo.findByAccountNumber(fromAccNum);
        Account accountToCredit = accountRepo.findByAccountNumber(accNum);
        if (fromAcc.getBalance().getAmount().compareTo(request.getAmount()) > 0) {
            accountToCredit.getBalance().setAmount(accountToCredit.getBalance().getAmount().add(request.getAmount()));
            fromAcc.getBalance().setAmount(fromAcc.getBalance().getAmount().subtract(request.getAmount()));
            accountRepo.save(fromAcc);
            accountRepo.save(accountToCredit);
        } else {
            System.out.println(fromAcc + "OOps you do not have enough amount::::::::::::::::::::::::::::::::::");
        }

        return fromAcc;

    }

    // This is functin deposits amount to an account

    @Override
    public Account depositAmount(DoDepositDTO request) {

        String accountNumber = request.getAccount();

        BigInteger accNum = null;

        if (accountNumber != null && accountNumber.length() > 1) {
            accNum = new BigInteger(accountNumber);
        }

        Account accountToDeposit = accountRepo.findByAccountNumber(accNum);
        accountToDeposit.getBalance().setAmount(accountToDeposit.getBalance().getAmount().add(request.getAmount()));;
        accountRepo.save(accountToDeposit);


        return accountToDeposit;

    }

    private boolean existsByAccount(BigInteger account) {
        return accountRepo.existsByAccountNumber(account);
    }

}
