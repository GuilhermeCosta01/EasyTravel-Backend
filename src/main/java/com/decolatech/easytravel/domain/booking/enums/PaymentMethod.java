package com.decolatech.easytravel.domain.booking.enums;

public enum PaymentMethod {
    DEBIT(0),
    CREDIT(1),
    BANK_SLIP(2),
    PIX(3);

    private final int value;

    PaymentMethod(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PaymentMethod fromValue(int value) {
        for (PaymentMethod method : PaymentMethod.values()) {
            if (method.getValue() == value) {
                return method;
            }
        }
        throw new IllegalArgumentException("Invalid PaymentMethod value: " + value);
    }
}
