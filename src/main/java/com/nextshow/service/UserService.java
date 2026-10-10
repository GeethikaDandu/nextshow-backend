package com.nextshow.service;

import com.nextshow.dto.LoginRequest;
import com.nextshow.dto.LoginResponse;
import com.nextshow.dto.RegisterUserRequest;
import com.nextshow.dto.UserResponse;
import com.nextshow.entity.User;
import com.nextshow.exception.AuthenticationException;
import com.nextshow.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }
public User getUserByEmail(String email) {

    return userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new RuntimeException("User not found"));
}
    // =========================
    // USER REGISTRATION
    // =========================

    public UserResponse registerUser(RegisterUserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setPhone(request.getPhone());

        User savedUser = userRepository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getPhone()
        );
    }

    // =========================
    // USER LOGIN
    // =========================

    public LoginResponse loginUser(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new AuthenticationException(
                                "Invalid email or password"
                        )
                );

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new AuthenticationException(
                    "Invalid email or password"
            );
        }

        // Generate JWT after successful login
        String token = jwtService.generateToken(
                user.getEmail()
        );

        return new LoginResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                "Login successful",
                token
        );
    }
}