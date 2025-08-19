package com.decolatech.easytravel.domain.bundle.exception;

public class NoMediaUpdateException extends RuntimeException {
    public NoMediaUpdateException() {
        super("Nenhuma atualização foi realizada na mídia.");
    }
}

