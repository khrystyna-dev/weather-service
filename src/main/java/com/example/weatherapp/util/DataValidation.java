package com.example.weatherapp.util;

import com.example.weatherapp.dto.UserRegisterDto;
import com.example.weatherapp.exception.CustomDataValidationException;

import static java.util.Objects.isNull;

public class DataValidation {
    private static final String STRING_PATTERN = "^[a-zA-Z]+$";
    private static final String PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
    private static final String EMAIL_PATTERN = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$";

    public static void objectValidation(UserRegisterDto dto) {
        if (isNull(dto.getEmail()) || isNull(dto.getPassword()) || isNull(dto.getFirstName()) || isNull(dto.getLastName())) {
            throw new CustomDataValidationException("None of the fields should be empty");
        }
        if (dto.getFirstName().length() < 2 || dto.getFirstName().length() > 6 || !dto.getFirstName().matches(STRING_PATTERN)) {
            throw new CustomDataValidationException(
                    "Invalid firstName. It should consist only of alphabet characters and of 2 to 6 characters - " + dto.getFirstName());
        }
        if (dto.getLastName().length() < 2 || dto.getLastName().length() > 6 || !dto.getLastName().matches(STRING_PATTERN)) {
            throw new CustomDataValidationException(
                    "Invalid lastName. It should consist only of alphabet characters and of 2 to 6 characters - " + dto.getLastName());
        }
        if (!dto.getEmail().matches(EMAIL_PATTERN)) {
            throw new CustomDataValidationException("Invalid email: " + dto.getEmail());
        }
        if (!dto.getPassword().matches(PASSWORD_PATTERN)) {
            throw new CustomDataValidationException("Invalid password! The password requires at least one lowercase character, one "
                    + "uppercase character, one number, and at least one special character. The password should consist of 8 to 20 characters " + dto.getPassword());
        }

    }
}
