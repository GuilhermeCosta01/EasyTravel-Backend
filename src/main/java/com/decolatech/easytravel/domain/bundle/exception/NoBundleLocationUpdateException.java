package com.decolatech.easytravel.domain.bundle.exception;

public class NoBundleLocationUpdateException extends RuntimeException {
    public NoBundleLocationUpdateException() {
        super("Nenhuma atualização foi realizada na relação pacote-localização.");
    }
}

