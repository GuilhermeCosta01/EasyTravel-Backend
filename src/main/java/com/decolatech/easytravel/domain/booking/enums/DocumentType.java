package com.decolatech.easytravel.domain.booking.enums;



public enum DocumentType {

    CPF(0),

    PASSPORT(1);

    private final int value;

    DocumentType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static DocumentType fromValue(int value) {
        for (DocumentType type : DocumentType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid DocumentType value: " + value);
    }
}
