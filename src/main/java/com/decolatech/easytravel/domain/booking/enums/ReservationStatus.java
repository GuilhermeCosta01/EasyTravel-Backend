package com.decolatech.easytravel.domain.booking.enums;


public enum ReservationStatus {

    PENDING(0),

    CONFIRMED(1),

    CANCELLED(2);

    private final int value;

    ReservationStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ReservationStatus fromValue(int value) {
        for (ReservationStatus status : ReservationStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid ReservationStatus value: " + value);
    }
}
