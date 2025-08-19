package com.decolatech.easytravel.domain.user.enums;

public enum UserStatus {
    DEACTIVATED(0),
    ACTIVATED(1);

    private final int value;

    UserStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static UserStatus fromValue(int value) {
        for (UserStatus status : UserStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid UserStatus value: " + value);
    }
}
