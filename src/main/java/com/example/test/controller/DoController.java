package com.example.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.dto.DoDepositDTO;
import com.example.test.dto.DoTransDto;
import com.example.test.dto.UserRequestDTO;
import com.example.test.exception.AlreadyExistsException;
import com.example.test.model.Account;
import com.example.test.model.Users;
import com.example.test.model.WalletBalance;
import com.example.test.response.ApiResponse;
import com.example.test.service.ServiceCall;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DoController {

    private final ServiceCall uServiceCall;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createUser(@RequestBody UserRequestDTO request) {

        try {
            Users users = uServiceCall.createUserAndAccount(request);
            return ResponseEntity.ok(new ApiResponse("Congratulations! Your account number is: %s".formatted(users.getAccount().getAccountNumber()), users));

        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage(), request));
        }

    }



     @PostMapping("/deposit")
    public ResponseEntity<ApiResponse> accountDeposit(@RequestBody DoDepositDTO request){
        try {
            Account balance = uServiceCall.depositAmount(request);
            return ResponseEntity.ok(new ApiResponse("Account has been deposited!", balance));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage(), null));
        }
    }



    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse> accountTransfer(@RequestBody DoTransDto request){
        try {
            Account balance = uServiceCall.doIntraTransfer(request);
            return ResponseEntity.ok(new ApiResponse("Account has been credtied!", balance));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage(), request.getToAccount()));
        }
    }

}
