package com.iassure.incident.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iassure.tenant.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private String username;
    private String email;
    @JsonIgnore
    private String password;
    private String role;
}
