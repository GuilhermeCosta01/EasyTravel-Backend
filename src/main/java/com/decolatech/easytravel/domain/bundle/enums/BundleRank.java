package com.decolatech.easytravel.domain.bundle.enums;

public enum BundleRank {
    BRONZE(0),
    SILVER(1),
    GOLD(2),
    PLATINUM(3);

    private final int value;

    BundleRank(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static BundleRank fromValue(int value) {
        for (BundleRank rank : BundleRank.values()) {
            if (rank.getValue() == value) {
                return rank;
            }
        }
        throw new IllegalArgumentException("Invalid BundleRank value: " + value);
    }
}
