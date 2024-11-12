package com.example.loginauthapi.services;

import com.example.loginauthapi.entity.User;

import com.example.loginauthapi.dto.LoginRequestDTO;
import com.example.loginauthapi.dto.RegisterRequestDTO;
import com.example.loginauthapi.dto.ResponseDTO;
import com.example.loginauthapi.infra.security.TokenService;
import com.example.loginauthapi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public ResponseDTO login(LoginRequestDTO body) {
        User user = userRepository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = tokenService.generateToken(user);
            return new ResponseDTO(user.getName(), token);
        }

        throw new RuntimeException("Invalid credentials");
    }

    public ResponseDTO register(RegisterRequestDTO body) {
        if (userRepository.findByEmail(body.email()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        User newUser = new User();
        newUser.setPassword(passwordEncoder.encode(body.password()));
        newUser.setEmail(body.email());
        newUser.setName(body.name());

        userRepository.save(newUser);

        String token = tokenService.generateToken(newUser);
        return new ResponseDTO(newUser.getName(), token);
    }
}

