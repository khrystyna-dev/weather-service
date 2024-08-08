package com.example.weatherapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {
    @Size(min = 2, max = 6)
    private String firstName;
    @Size(min = 2, max = 6)
    private String lastName;
    @Email
    private String email;
    @Size(min = 8)
    private String password;
}

