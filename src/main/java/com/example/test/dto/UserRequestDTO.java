package com.example.test.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    @NotBlank(message="Email is required")
    @Email(message = "Invalid email")
    private String email;
     @NotBlank(message="First name is required")
    private String firstname;
     @NotBlank(message="Last name is required")
    private String lastname;
     @NotBlank(message="Phone number is required")
    private String phoneNumber;
    
}
