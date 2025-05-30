package com.TaskLogger.logger.service.impl;

import com.TaskLogger.logger.DTOs.UserLoginResponseDTO;
import com.TaskLogger.logger.DTOs.request.UserLoginRequestDTO;
import com.TaskLogger.logger.DTOs.request.UserRegisterRequestDTO;
import com.TaskLogger.logger.entity.User;
import com.TaskLogger.logger.repository.UserRepository;
import com.TaskLogger.logger.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public String registerUser(UserRegisterRequestDTO userRegisterRequestDTO) {

        User user = User.builder()
                .email(userRegisterRequestDTO.getEmail())
                .name(userRegisterRequestDTO.getName())
                .password(passwordEncoder.encode(userRegisterRequestDTO.getPassword()))
                .build();
        userRepository.save(user);

        return "success";
    }

    @Override
    public UserLoginResponseDTO loginUser(UserLoginRequestDTO userLoginRequestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginRequestDTO.getEmail(),
                        userLoginRequestDTO.getPassword()
                )
        );
        System.out.println("Authentication successful for user: " + userLoginRequestDTO.getEmail());
        User user = userRepository.findByEmail(userLoginRequestDTO.getEmail())
                                  .orElseThrow(() -> new RuntimeException("İstifadəçi tapılmadı"));
        System.out.println(user.toString());
        String token = jwtService.generateToken(user);
        System.out.println(token);
        return new UserLoginResponseDTO(token);
    }

}