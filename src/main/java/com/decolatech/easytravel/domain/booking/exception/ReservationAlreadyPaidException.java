package com.decolatech.easytravel.domain.booking.exception;

public class ReservationAlreadyPaidException extends RuntimeException {
    public ReservationAlreadyPaidException(String message) {
        super(message);
    }
}

