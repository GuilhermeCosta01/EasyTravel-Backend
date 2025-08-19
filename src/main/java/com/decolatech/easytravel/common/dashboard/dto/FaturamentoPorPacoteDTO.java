package com.decolatech.easytravel.common.dashboard.dto;

public class FaturamentoPorPacoteDTO {
    private int pacoteId;
    private double faturamento;

    public FaturamentoPorPacoteDTO(int pacoteId, double faturamento) {
        this.pacoteId = pacoteId;
        this.faturamento = faturamento;
    }

    public int getPacoteId() {
        return pacoteId;
    }

    public void setPacoteId(int pacoteId) {
        this.pacoteId = pacoteId;
    }

    public double getFaturamento() {
        return faturamento;
    }

    public void setFaturamento(double faturamento) {
        this.faturamento = faturamento;
    }
}

