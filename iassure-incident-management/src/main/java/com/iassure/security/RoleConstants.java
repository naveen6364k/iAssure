package com.iassure.security;

public final class RoleConstants {
    private RoleConstants() {}

    public static final String SUPER_ADMIN = "hasRole('SUPER_ADMIN')";
    public static final String ADMIN = "hasRole('ADMIN')";
    public static final String MANAGER = "hasRole('MANAGER')";
    public static final String USER = "hasRole('USER')";
    public static final String READONLY = "hasRole('READONLY')";
}
