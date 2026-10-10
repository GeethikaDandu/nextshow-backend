package com.nextshow.controller;

import com.nextshow.dto.LoginRequest;
import com.nextshow.dto.LoginResponse;
import com.nextshow.dto.RegisterUserRequest;
import com.nextshow.dto.UserResponse;
import com.nextshow.entity.User;
import com.nextshow.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @RequestBody RegisterUserRequest request) {

        return ResponseEntity.ok(
                userService.registerUser(request)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                userService.loginUser(request)
        );
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getProfile() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userService.getUserByEmail(email);

        UserResponse response = new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone()
        );

        return ResponseEntity.ok(response);
    }
}