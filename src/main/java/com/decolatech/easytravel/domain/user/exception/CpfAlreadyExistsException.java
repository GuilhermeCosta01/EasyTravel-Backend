package com.decolatech.easytravel.domain.user.exception;

public class CpfAlreadyExistsException extends RuntimeException {
    public CpfAlreadyExistsException(String cpf) {
        super("CPF '" + cpf + "' já está sendo usado por outro usuário");
    }
}
