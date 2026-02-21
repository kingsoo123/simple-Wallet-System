package com.example.test.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data@AllArgsConstructor@NoArgsConstructor@ToString
public class DoDepositDTO {

     @NotBlank(message="Account is required")
    private String account;
     @NotBlank(message="Amount is required")
    private BigDecimal amount;
    
}
