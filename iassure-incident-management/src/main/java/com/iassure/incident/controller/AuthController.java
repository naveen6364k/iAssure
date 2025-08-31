package com.iassure.incident.controller;

import com.iassure.incident.dto.LoginRequest;
import com.iassure.incident.dto.LoginResponse;
import com.iassure.incident.entity.User;
import com.iassure.incident.repository.UserRepository;
import com.iassure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        final User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow();
        final String token = jwtUtil.generateToken(user.getUsername(), user.getRole(), user.getTenantId());

        return ResponseEntity.ok(new LoginResponse(token));
    }
}
