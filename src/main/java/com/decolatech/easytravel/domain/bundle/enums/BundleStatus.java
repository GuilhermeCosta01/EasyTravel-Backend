package com.decolatech.easytravel.domain.bundle.enums;

public enum BundleStatus {
    AVAILABLE(0),
    UNAVAILABLE(1);

    private final int value;

    BundleStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static BundleStatus fromValue(int value) {
        for (BundleStatus status : BundleStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid BundleStatus value: " + value);
    }
}
