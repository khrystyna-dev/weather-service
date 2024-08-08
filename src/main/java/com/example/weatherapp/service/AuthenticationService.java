package com.example.weatherapp.service;

import com.example.weatherapp.dto.UserRegisterDto;
import com.example.weatherapp.model.User;
import org.springframework.security.core.AuthenticationException;

public interface AuthenticationService {
    User register(UserRegisterDto dto);

    void login(String username, String password) throws AuthenticationException;

    void verification(String code);
}
