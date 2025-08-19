package com.decolatech.easytravel.domain.bundle.enums;

public enum MediaType {
    IMAGE(0),
    VIDEO(1);

    private final int value;

    MediaType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static MediaType fromValue(int value) {
        for (MediaType type : MediaType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid MediaType value: " + value);
    }
}
