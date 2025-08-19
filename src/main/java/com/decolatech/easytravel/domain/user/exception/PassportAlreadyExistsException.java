package com.decolatech.easytravel.domain.user.exception;

public class PassportAlreadyExistsException extends RuntimeException {
    public PassportAlreadyExistsException(String passport) {
        super("Passport '" + passport + "' já está sendo usado por outro usuário");
    }
}
