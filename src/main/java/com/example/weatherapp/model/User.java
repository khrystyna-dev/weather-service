package com.example.weatherapp.model;

import jakarta.persistence.*;
import lombok.Data;



@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "account_verified")
    private boolean accountVerified;
    private String verificationCode;
}
