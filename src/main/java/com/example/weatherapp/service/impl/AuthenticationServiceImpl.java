package com.example.weatherapp.service.impl;

import com.example.weatherapp.dto.UserRegisterDto;
import com.example.weatherapp.model.User;
import com.example.weatherapp.service.AuthenticationService;
import com.example.weatherapp.service.UserService;
import com.example.weatherapp.service.VerificationFormSender;
import com.example.weatherapp.util.DataValidation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final ObjectMapper mapper;
    private final VerificationFormSender sender;

    public AuthenticationServiceImpl(PasswordEncoder passwordEncoder,
                                     UserService userService,
                                     VerificationFormSender sender) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.mapper = new ObjectMapper();
        this.sender = sender;
    }


    public User register(UserRegisterDto registerDto) {
        DataValidation.objectValidation(registerDto);
        User user = mapper.convertValue(registerDto, User.class);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setAccountVerified(false);
        user.setVerificationCode(passwordEncoder.encode(getVerificationCode()));
        sender.sendMessage(user);
        return userService.add(user);
    }

    @Override
    public void login(String username, String password) {
        Optional<User> user = userService.getByEmail(username);
        if (user.isEmpty()|| !passwordEncoder.matches(password, user.get().getPassword())) {
            throw new AuthenticationException("Invalid email or password") {
                @Override
                public String getMessage() {
                    return super.getMessage();
                }
            };
        }
        if (!user.get().isAccountVerified()) {
            throw new AuthenticationException("User is not verified") {
                @Override
                public String getMessage() {
                    return super.getMessage();
                }
            };
        }
    }

    @Override
    public void verification(String code) {
        Optional<User> userOptional = userService.findByCode(code);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setAccountVerified(true);
            userService.add(user);
        }
    }

    private String getVerificationCode() {
        UUID uuid = UUID.randomUUID();
        return Base64.getUrlEncoder().withoutPadding().encodeToString(uuid.toString().getBytes());
    }
}
