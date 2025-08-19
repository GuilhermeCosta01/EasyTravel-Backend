package com.decolatech.easytravel.common.dashboard.dto;

public class ReceitaPorMesDTO {
    private String mes;
    private double faturamento;

    public ReceitaPorMesDTO(String mes, double faturamento) {
        this.mes = mes;
        this.faturamento = faturamento;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public double getFaturamento() {
        return faturamento;
    }

    public void setFaturamento(double faturamento) {
        this.faturamento = faturamento;
    }
}

