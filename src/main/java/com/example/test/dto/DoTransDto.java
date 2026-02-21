package com.example.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;

@Data@AllArgsConstructor@NoArgsConstructor@ToString
public class DoTransDto {
    @NotBlank(message="Account is required")
    private String fromAccount;
     @NotBlank(message="Account is required")
    private String toAccount;
     @NotBlank(message="Amount is required")
    private BigDecimal amount;
}
