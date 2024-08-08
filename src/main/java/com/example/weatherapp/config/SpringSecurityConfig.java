package com.example.weatherapp.config;

import com.example.weatherapp.security.CustomAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {
    private final AuthenticationFailureHandler failureHandler;
    private final CustomAuthenticationProvider provider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authz -> authz
            .requestMatchers("/login", "/error","/register", "/verified").permitAll()
            .anyRequest().authenticated()
        );

        http.formLogin(authz -> authz
            .loginPage("/login").failureHandler(failureHandler).permitAll()
        );

        http.logout(authz -> authz
            .deleteCookies("JSESSIONID")
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        );

        http.csrf(Customizer.withDefaults())
            .authenticationProvider(provider);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> {
            web.ignoring().requestMatchers("/*.css");
            web.ignoring().requestMatchers("/*.js");
            web.ignoring().requestMatchers("/*.json");
            web.ignoring().requestMatchers("/*.ico");
            web.ignoring().requestMatchers("/*.tff");
            web.ignoring().requestMatchers("/*.woff");
            web.ignoring().requestMatchers("/*.woff2");
            web.ignoring().requestMatchers("/*.eot");
            web.ignoring().requestMatchers("/*.otf");
        };
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
