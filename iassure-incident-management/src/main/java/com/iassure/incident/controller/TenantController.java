package com.iassure.incident.controller;

import com.iassure.incident.dto.CreateTenantRequest;
import com.iassure.incident.entity.Tenant;
import com.iassure.incident.repository.TenantRepository;
import com.iassure.security.RoleConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tenants")
@RequiredArgsConstructor
@Tag(name = "Tenant Management", description = "APIs for managing tenants")
public class TenantController {

    private final TenantRepository tenantRepository;

    @PostMapping
    @PreAuthorize(RoleConstants.SUPER_ADMIN)
    @Operation(summary = "Create a new tenant")
    @ApiResponse(responseCode = "201", description = "Tenant created successfully")
    public ResponseEntity<Tenant> createTenant(@RequestBody CreateTenantRequest createTenantRequest) {
        Tenant tenant = Tenant.builder()
                .id(createTenantRequest.getId())
                .name(createTenantRequest.getName())
                .build();
        Tenant savedTenant = tenantRepository.save(tenant);
        return new ResponseEntity<>(savedTenant, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize(RoleConstants.SUPER_ADMIN)
    @Operation(summary = "Get all tenants")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of tenants")
    public ResponseEntity<List<Tenant>> getAllTenants() {
        List<Tenant> tenants = tenantRepository.findAll();
        return ResponseEntity.ok(tenants);
    }
}
