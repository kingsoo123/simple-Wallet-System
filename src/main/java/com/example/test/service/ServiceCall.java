package com.example.test.service;

import java.math.BigDecimal;

import com.example.test.dto.DoDepositDTO;
import com.example.test.dto.DoTransDto;
import com.example.test.dto.UserRequestDTO;
import com.example.test.model.Account;
import com.example.test.model.Users;

public interface ServiceCall {

    Account doIntraTransfer(DoTransDto request);

    Users createUserAndAccount(UserRequestDTO user);

    Account depositAmount(DoDepositDTO request);

}
