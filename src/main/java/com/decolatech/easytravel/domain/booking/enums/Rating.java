package com.decolatech.easytravel.domain.booking.enums;



public enum Rating {


    ONE_STAR(0),

    TWO_STARS(1),

    THREE_STARS(2),

    FOUR_STARS(3),

    FIVE_STARS(4);

    private final int value;

    Rating(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Rating fromValue(int value) {
        for (Rating rating : Rating.values()) {
            if (rating.getValue() == value) {
                return rating;
            }
        }
        throw new IllegalArgumentException("Invalid Rating value: " + value);
    }
}




