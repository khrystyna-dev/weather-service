package com.example.weatherapp.impl;

import com.example.weatherapp.dto.UserRegisterDto;
import com.example.weatherapp.exception.CustomDataValidationException;
import com.example.weatherapp.model.User;
import com.example.weatherapp.service.UserService;
import com.example.weatherapp.service.VerificationFormSender;
import com.example.weatherapp.service.impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceImplTest {
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserService userService;
    @Mock
    private VerificationFormSender sender;
    @InjectMocks
    private AuthenticationServiceImpl authenticationService;
    private List<UserRegisterDto> validUsers;
    private List<UserRegisterDto> invalidFirstName;
    private List<UserRegisterDto> invalidLastName;
    private List<UserRegisterDto> invalidEmail;
    private List<UserRegisterDto> invalidPassword;
    private List<UserRegisterDto> dataIsEmpty;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validUsers = TestDataProvider.getValidUsers();
        invalidFirstName = TestDataProvider.getUserWithInvalidFirstName();
        invalidLastName = TestDataProvider.getUserWithInvalidLastName();
        invalidEmail = TestDataProvider.getUserWithInvalidEmail();
        invalidPassword = TestDataProvider.getUserWithInvalidPassword();
        dataIsEmpty = TestDataProvider.getUserWithDataIsEmpty();
    }

    @Test
    void register_ok() {
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        when(userService.add(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        List<User> registeredUsers = validUsers.stream()
                .map(user -> authenticationService.register(user))
                .toList();

        registeredUsers.forEach(Assertions::assertNotNull);
        registeredUsers.forEach(user -> assertFalse(user.isAccountVerified()));

        validUsers.forEach(user -> {
            verify(passwordEncoder).encode(user.getPassword());
            verify(passwordEncoder, times(validUsers.size() * 2)).encode(anyString());
        });

        registeredUsers.forEach(user -> {
            verify(userService, times(validUsers.size())).add(any(User.class));
            verify(sender, times(validUsers.size())).sendMessage(any(User.class));
        });
    }

    @Test
    void register_invalidCredentials() {
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        when(userService.add(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        invalidFirstName.forEach(user -> {
            CustomDataValidationException exception = assertThrows(CustomDataValidationException.class,
                    () -> authenticationService.register(user));
            assertEquals("Invalid firstName. It should consist only of alphabet characters and of 2 to 6 characters - "
                    + user.getFirstName(), exception.getMessage());
        });

        invalidLastName.forEach(user ->{
            CustomDataValidationException exception = assertThrows(CustomDataValidationException.class,
                    () -> authenticationService.register(user));
            assertEquals("Invalid lastName. It should consist only of alphabet characters and of 2 to 6 characters - "
                    + user.getLastName(), exception.getMessage());
        });

        invalidEmail.forEach(user -> {
            CustomDataValidationException exception = assertThrows(CustomDataValidationException.class,
                    () -> authenticationService.register(user));
            assertEquals("Invalid email: " + user.getEmail(), exception.getMessage());
        });

        invalidPassword.forEach(user -> {
            CustomDataValidationException exception = assertThrows(CustomDataValidationException.class,
                    () -> authenticationService.register(user));
            assertEquals("Invalid password! The password requires at least one lowercase character, one "
                    + "uppercase character, one number, and at least one special character. "
                            + "The password should consist of 8 to 20 characters " + user.getPassword(),
                    exception.getMessage());
        });

        dataIsEmpty.forEach(user -> {
            CustomDataValidationException exception = assertThrows(CustomDataValidationException.class,
                    () -> authenticationService.register(user));
            assertEquals("None of the fields should be empty",
                    exception.getMessage());
        });

    }

    @Test
    void testLogin() {
        String username = "test@example.com";
        String password = "testPassword";
        User user = new User();
        user.setEmail(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setAccountVerified(true);

        when(userService.getByEmail(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);

        authenticationService.login(username, password);
    }

    @Test
    void login_ValidCredentialsAndVerifiedUser() {
        String username = "test@example.com";
        String password = "password";
        User user = new User();
        user.setEmail(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setAccountVerified(true);

        when(userService.getByEmail(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);

        assertDoesNotThrow(() -> authenticationService.login(username, password));
    }

    @Test
    void login_InvalidPassword() {
        String username = "test@example.com";
        String password = "wrongPassword";
        User user = new User();
        user.setEmail(username);
        user.setPassword(passwordEncoder.encode("correctPassword")); // Correct password is different

        when(userService.getByEmail(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(false);

        AuthenticationException exception = assertThrows(AuthenticationException.class,
                () -> authenticationService.login(username, password));

        assertEquals("Invalid email or password", exception.getMessage());
    }

    @Test
    void login_UserNotVerified() {
        String username = "test@example.com";
        String password = "password";
        User user = new User();
        user.setEmail(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setAccountVerified(false);

        when(userService.getByEmail(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);

        AuthenticationException exception = assertThrows(AuthenticationException.class,
                () -> authenticationService.login(username, password));

        assertEquals("User is not verified", exception.getMessage());
    }

    @Test
    void login_UserNotFound() {
        String username = "nonexistent@example.com";

        when(userService.getByEmail(username)).thenReturn(Optional.empty());

        AuthenticationException exception = assertThrows(AuthenticationException.class,
                () -> authenticationService.login(username, "password"));

        assertEquals("Invalid email or password", exception.getMessage());
    }

    @Test
    void testLoginWithInvalidCredentials() {
        String username = "test@example.com";
        String password = "testPassword";
        when(userService.getByEmail(username)).thenReturn(Optional.empty());

        assertThrows(AuthenticationException.class, () -> authenticationService.login(username, password));
    }

    @Test
    void testLoginWithUnverifiedAccount() {
        String username = "test@example.com";
        String password = "testPassword";
        User user = new User();
        user.setEmail(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setAccountVerified(false);

        when(userService.getByEmail(username)).thenReturn(Optional.of(user));

        assertThrows(AuthenticationException.class, () -> authenticationService.login(username, password));
    }

    @Test
    void testVerification() {
        String verificationCode = "123456789012";
        User user = new User();
        user.setVerificationCode(verificationCode);
        when(userService.findByCode(verificationCode)).thenReturn(Optional.of(user));

        authenticationService.verification(verificationCode);

        assertTrue(user.isAccountVerified());
        verify(userService, times(1)).add(user);
    }

    private ClassLoaderTemplateResolver templateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    private SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }
}
