package com.decolatech.easytravel.domain.bundle.exception;

public class NoLocationUpdateException extends RuntimeException {
    public NoLocationUpdateException() {
        super("Nenhuma atualização foi realizada na localização.");
    }
}

