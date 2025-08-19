package com.decolatech.easytravel.domain.user.enums;

public enum UserRole {
    USER(0),
    EMPLOYEE(1),
    ADMIN(2);

    private final int value;

    UserRole(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static UserRole fromValue(int value) {
        for (UserRole role : UserRole.values()) {
            if (role.getValue() == value) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid UserRole value: " + value);
    }
}
