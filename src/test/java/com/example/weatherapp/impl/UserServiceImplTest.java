package com.example.weatherapp.impl;

import com.example.weatherapp.model.User;
import com.example.weatherapp.repository.UserRepository;
import com.example.weatherapp.service.UserService;
import com.example.weatherapp.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceImplTest {
    private UserRepository repository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(UserRepository.class);
        userService = new UserServiceImpl(repository);
    }

    @Test
    void getUserByEmail_ok() {
        String email = "test@gmail.com";
        User expectedUser = new User();

        when(repository.findByEmail(email)).thenReturn(Optional.of(expectedUser));

        Optional<User> actualUser = userService.getByEmail(email);

        assertTrue(actualUser.isPresent());
        assertEquals(expectedUser, actualUser.get());
        verify(repository).findByEmail(email);
    }

    @Test
    void getUserByEmail_notOk() {
        String email = "nonexistent@gmail.com";

        when(repository.findByEmail(email)).thenReturn(Optional.empty());

        Optional<User> expectedUser = userService.getByEmail(email);

        assertTrue(expectedUser.isEmpty());
        verify(repository).findByEmail(email);
    }

    @Test
    void addUserToDb_ok() {
        User userToSave = new User();
        User savedUser = new User();

        when(repository.save(userToSave)).thenReturn(savedUser);
        User result = userService.add(userToSave);
        assertNotNull(result);
        verify(repository).save(userToSave);
        assertEquals(savedUser, result);
    }

    @Test
    void findByCode_ok() {
        String code = "verificationCode";
        User expectedUser = new User();

        when(repository.findByVerificationCode(code)).thenReturn(Optional.of(expectedUser));

        Optional<User> actualUser = userService.findByCode(code);

        assertTrue(actualUser.isPresent());
        assertEquals(expectedUser, actualUser.get());
        verify(repository).findByVerificationCode(code);
    }

    @Test
    void findByCode_notOk() {
        String code = "nonexistentCode";

        when(repository.findByVerificationCode(code)).thenReturn(Optional.empty());

        Optional<User> actualUser = userService.findByCode(code);

        assertTrue(actualUser.isEmpty());
        verify(repository).findByVerificationCode(code);
    }
}