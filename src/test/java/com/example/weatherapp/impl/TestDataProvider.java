package com.example.weatherapp.impl;

import com.example.weatherapp.dto.UserRegisterDto;

import java.util.List;

public class TestDataProvider {

    public static List<UserRegisterDto> getValidUsers() {
        return List.of(
                new UserRegisterDto("John", "Doe", "john.doe@gmail.com", "P@ssw0rd"),
                new UserRegisterDto("Alice", "Smith", "alice.smith@gmail.com", "Secret123!"),
                new UserRegisterDto("Bob", "John", "bob.johnson@gmail.com", "Str0ngP@ss"),
                new UserRegisterDto("Sophia", "Miller", "sophia.miller@gmail.com", "MyP@ssw0rd"),
                new UserRegisterDto("Oliver", "Brown", "oliver.brown@gmail.com", "P@ssw0rd123"),
                new UserRegisterDto("Emma", "Ander", "emma.anderson@gmail.com", "Pa$$w0rd"),
                new UserRegisterDto("Will", "Lee", "william.lee@gmail.com", "SecurePass123!"),
                new UserRegisterDto("Ava", "Jones", "ava.jones@gmail.com", "Password!123"),
                new UserRegisterDto("Ethan", "Wilson", "ethan.wilson@gmail.com", "Str0ngPassword!"),
                new UserRegisterDto("Mia", "Taylor", "mia.taylor@gmail.com", "P@ssword123!"),
                new UserRegisterDto("James", "Moore", "james.moore@gmail.com", "SecretP@s0s"),
                new UserRegisterDto("Char", "Hill", "charlotte.hill@gmail.com", "Secure123!"),
                new UserRegisterDto("Liam", "Evans", "liam.evans@gmail.com", "MySecurePass!2"),
                new UserRegisterDto("Grace", "Parker", "grace.parker@gmail.com", "Pa$$word123"),
                new UserRegisterDto("Logan", "Carter", "logan.carter@gmail.com", "StrongP@ssw0rd"),
                new UserRegisterDto("Avery", "Wright", "avery.wright@gmail.com", "SecureP4ass!"),
                new UserRegisterDto("Harper", "Cooper", "harper.cooper@gmail.com", "Pass1234!"),
                new UserRegisterDto("Jack", "Reed", "jackson.reed@gmail.com", "P@ssw0rd!"),
                new UserRegisterDto("Sofia", "Kelly", "sofia.kelly@gmail.com", "StrongP@ss123"),
                new UserRegisterDto("Lucas", "Barnes", "lucas.barnes@gmail.com", "Secure0Pa$$"),
                new UserRegisterDto("Aria", "Fisher", "aria.fisher@gmail.com", "Pa$$word1!"),
                new UserRegisterDto("Elijah", "Perry", "elijah.perry@gmail.com", "SecurePass1!"),
                new UserRegisterDto("Amelia", "Ward", "amelia.ward@gmail.com", "Strong2P@ss!"),
                new UserRegisterDto("Liam", "Turner", "liam.turner@gmail.com", "Pa$$w0rd!"),
                new UserRegisterDto("Ava", "Baker", "ava.baker@gmail.com", "SecureP@ss1")
        );
    }

    public static List<UserRegisterDto> getUserWithInvalidFirstName() {
        return List.of(
                new UserRegisterDto("J", "Doe", "john.doe@gmail.com", "P@ssw0rd"),
                new UserRegisterDto("Aliceee", "Smith", "alice.smith@gmail.com", "Secret123"),
                new UserRegisterDto("o", "Johnson", "bob.johnson@gmail.com", "StrongP@ss"),
                new UserRegisterDto("Sophiaerr", "Miller", "sophia.miller@gmail.com", "MyP@ssword"),
                new UserRegisterDto("r", "Brown", "oliver.brown@gmail.com", "P@ssw0rd123")
        );
    }

    public static List<UserRegisterDto> getUserWithInvalidLastName() {
        return List.of(
                new UserRegisterDto("Sophia", "M!ller", "sophia.miller@gmail.com", "MyP@ssword"),
                new UserRegisterDto("Oliver", "B", "oliver.brown@gmail.com", "P@ssw0rd123"),
                new UserRegisterDto("Emma", "Ander_son", "emma.anderson@gmail.com", "Pa$$w0rd"),
                new UserRegisterDto("Ava", "Jone$$$$", "ava.jones@gmail.com", "Password!123"),
                new UserRegisterDto("Ethan", "W!lson", "ethan.wilson@gmail.com", "StrongPassword")
        );
    }

    public static List<UserRegisterDto> getUserWithInvalidEmail() {
        return List.of(
                new UserRegisterDto("Mia", "Taylor", "mia.taylor!gmail.com", "P@ssword123"),
                new UserRegisterDto("James", "Moore", "james.moore", "SecretP@ss"),
                new UserRegisterDto("Liam", "Evans", "liam.evansgmail.com", "MySecurePass"),
                new UserRegisterDto("Grace", "Parker", "grace.parker@gmail", "Pa$$word123"),
                new UserRegisterDto("Logan", "Carter", "logan.carter@gmailcom", "StrongP@ssword")
        );
    }

    public static List<UserRegisterDto> getUserWithInvalidPassword() {
        return List.of(
                new UserRegisterDto("Avery", "Wright", "avery.wright@gmail.com", "securepass!"),
                new UserRegisterDto("Harper", "Cooper", "harper.cooper@gmail.com", "strongP@sswoooooooooord!"),
                new UserRegisterDto("Jack", "Reed", "jackson.reed@gmail.com", "P@ssw"),
                new UserRegisterDto("Sofia", "Kelly", "sofia.kelly@gmail.com", "Strongp@ss"),
                new UserRegisterDto("Lucas", "Barnes", "lucas.barnes@gmail.com", "SecurePa$$")
        );
    }
    public static List<UserRegisterDto> getUserWithDataIsEmpty() {
        return List.of(
                new UserRegisterDto("Aria", null, "aria.fisher@gmail.com", "Pa$$wor2d!"),
                new UserRegisterDto("Elijah", "Perry", null, "Secu@!3re"),
                new UserRegisterDto(null, "Ward", "amelia.ward@gmail.com", null),
                new UserRegisterDto("Liam", "Turner", "liam.turner@gmail.com", null),
                new UserRegisterDto("Ava", null, null, "SecureP@ss1!")
        );
    }
}
