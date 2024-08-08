package com.example.weatherapp.service;

import com.example.weatherapp.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getByEmail(String email);

    User add(User user);

    Optional<User> findByCode(String code);
}
