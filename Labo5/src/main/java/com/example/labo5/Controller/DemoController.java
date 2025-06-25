package com.example.labo5.Controller;

import com.example.labo5.Domain.DTO.CreateUserDTO;
import com.example.labo5.Domain.DTO.KeycloakTokenResponse;
import com.example.labo5.Domain.DTO.LoginRequest;
import com.example.labo5.Service.iAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/demo")
public class DemoController {
    private final iAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid CreateUserDTO user) {
        try {
            KeycloakTokenResponse response = authService.register(user);
            return ResponseEntity.ok("User registered successfully: " + response.getAccessToken());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Registration failed: " + e.getMessage());
        }
    }


    @PostMapping("/login")
    public ResponseEntity<KeycloakTokenResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(
                loginRequest.getUsername(),
                loginRequest.getPassword()));
    }

    @PreAuthorize("hasRole('role-user')")
    @GetMapping("/auth-test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test endpoint is working!");
    }
}
