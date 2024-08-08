package com.example.weatherapp.lib;



import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DataValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDataLocation {
    String message() default "Invalid data";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};}