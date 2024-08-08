package com.example.weatherapp.security;

import com.example.weatherapp.exception.DataProcessingException;
import com.example.weatherapp.model.User;
import com.example.weatherapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static org.springframework.security.core.userdetails.User.withUsername;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository repository;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email).orElseThrow(
            () -> new DataProcessingException("Couldn't find user by email: " + email)
        );
        UserBuilder builder = withUsername(email);
        builder.password(user.getPassword());
        builder.authorities("USER");
        return builder.build();
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Cannot found user"));
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
//        AuthUser authUser = new AuthUser(username, user.getPassword(), authorities);
//        authUser.setId(user.getId().toString());
//        authUser.setDisplayName(user.getFirstName());
//        return authUser;
//    }

}