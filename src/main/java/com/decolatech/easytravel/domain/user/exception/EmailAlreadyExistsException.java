package com.decolatech.easytravel.domain.user.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super("Email '" + email + "' já está sendo usado por outro usuário");
    }
}
