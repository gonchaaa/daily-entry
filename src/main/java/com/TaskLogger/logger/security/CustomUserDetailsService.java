package com.TaskLogger.logger.security;

import com.TaskLogger.logger.entity.User;
import com.TaskLogger.logger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email)  {
        System.out.println("Loading user by email: " + email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {System.out.println("istifadeci melumati yoxdur") ;
        return new UsernameNotFoundException("User not found");});
        System.out.println("User found: " + user);
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities("USER")
                .build();
    }

}
