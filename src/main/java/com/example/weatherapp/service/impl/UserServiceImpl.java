package com.example.weatherapp.service.impl;

import com.example.weatherapp.model.User;
import com.example.weatherapp.repository.UserRepository;
import com.example.weatherapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    @Override
    public Optional<User> getByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public User add(User user) {
        return repository.save(user);
    }

    @Override
    public Optional<User> findByCode(String code) {
        return repository.findByVerificationCode(code);
    }
}
