package com.iassure.incident.controller;

import com.iassure.incident.dto.CreateUserRequest;
import com.iassure.incident.entity.User;
import com.iassure.incident.repository.UserRepository;
import com.iassure.security.RoleConstants;
import com.iassure.tenant.context.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @PostMapping
    @PreAuthorize(RoleConstants.ADMIN)
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
        String tenantId = TenantContext.getCurrentTenant();
        if (tenantId == null) {
            // Or handle as a bad request
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        User user = User.builder()
                .username(createUserRequest.getUsername())
                .email(createUserRequest.getEmail())
                .role(createUserRequest.getRole())
                .build();
        user.setTenantId(tenantId);
        User savedUser = userRepository.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize(RoleConstants.ADMIN)
    public ResponseEntity<List<User>> getAllUsers() {
        // The hibernate filter will automatically scope this to the current tenant
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
}
