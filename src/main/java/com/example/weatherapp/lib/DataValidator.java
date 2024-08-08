package com.example.weatherapp.lib;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValidator implements ConstraintValidator<ValidDataLocation, String> {
    private static final String DATA_VALIDATION_REGEX = "^\\d*\\.?\\d*$";

    @Override
    public boolean isValid(String lat, ConstraintValidatorContext context) {
        if (lat == null){
            return false;
        }
        Pattern pattern = Pattern.compile(DATA_VALIDATION_REGEX);
        Matcher matcher = pattern.matcher(lat);
        return matcher.matches();
    }
}