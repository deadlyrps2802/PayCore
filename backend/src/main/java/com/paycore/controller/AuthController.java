package com.paycore.controller;

import com.paycore.dto.ApiResponse;
import com.paycore.dto.AuthResponse;
import com.paycore.dto.LoginRequest;
import com.paycore.dto.RegisterRequest;
import com.paycore.entity.Role;
import com.paycore.entity.User;
import com.paycore.repository.UserRepository;
import com.paycore.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private AuthService authService;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            AuthResponse response = authService.login(loginRequest);
            return ResponseEntity.ok(new ApiResponse<>(true, "Login successful", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Invalid email or password"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Object>> register(@Valid @RequestBody RegisterRequest request) {
        String email = request.getEmail().trim().toLowerCase();
        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "An account with this email already exists"));
        }

        User user = new User(email, passwordEncoder.encode(request.getPassword()), Role.ROLE_EMPLOYEE);
        userRepository.save(user);
        return ResponseEntity.ok(new ApiResponse<>(true, "Account created successfully. You can now sign in.", null));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<Object>> getCurrentUser() {
        return ResponseEntity.ok(new ApiResponse<>(true, "User context retrieved", null));
    }
}
