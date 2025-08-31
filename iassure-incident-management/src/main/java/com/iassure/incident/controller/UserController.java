package com.iassure.incident.controller;

import com.iassure.incident.dto.CreateUserRequest;
import com.iassure.incident.entity.User;
import com.iassure.incident.repository.UserRepository;
import com.iassure.security.RoleConstants;
import com.iassure.tenant.context.TenantContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "APIs for managing users within a tenant")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    @PreAuthorize(RoleConstants.ADMIN)
    @Operation(summary = "Create a new user for the current tenant")
    @ApiResponse(responseCode = "201", description = "User created successfully")
    @ApiResponse(responseCode = "403", description = "Forbidden if tenant context is not set")
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
        String tenantId = TenantContext.getCurrentTenant();
        if (tenantId == null) {
            // Or handle as a bad request
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        User user = User.builder()
                .username(createUserRequest.getUsername())
                .email(createUserRequest.getEmail())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .role(createUserRequest.getRole())
                .build();
        user.setTenantId(tenantId);
        User savedUser = userRepository.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize(RoleConstants.ADMIN)
    @Operation(summary = "Get all users for the current tenant")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of users")
    public ResponseEntity<List<User>> getAllUsers() {
        // The hibernate filter will automatically scope this to the current tenant
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
}
