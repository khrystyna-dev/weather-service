package com.example.weatherapp.security;

import com.example.weatherapp.service.AuthenticationService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {
    private final AuthenticationService service;

    public CustomAuthenticationProvider(@Lazy AuthenticationService service, UserDetailsService userDetailsService,
                                        @Lazy PasswordEncoder encoder) {
        this.service = service;
        super.setUserDetailsService(userDetailsService);
        super.setPasswordEncoder(encoder);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        super.additionalAuthenticationChecks(userDetails, authentication);
        System.out.println(authentication.toString());
        service.login(authentication.getPrincipal().toString(), authentication.getCredentials().toString());
    }
}
