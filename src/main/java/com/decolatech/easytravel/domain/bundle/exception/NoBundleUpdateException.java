package com.decolatech.easytravel.domain.bundle.exception;

public class NoBundleUpdateException extends RuntimeException {
    public NoBundleUpdateException() {
        super("Nenhuma atualização foi realizada no pacote.");
    }
}

