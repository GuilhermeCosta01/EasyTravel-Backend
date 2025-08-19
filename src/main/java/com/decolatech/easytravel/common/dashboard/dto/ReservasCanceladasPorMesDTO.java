package com.decolatech.easytravel.common.dashboard.dto;

public class ReservasCanceladasPorMesDTO {
    private String mes;
    private long totalCanceladas;

    public ReservasCanceladasPorMesDTO(String mes, long totalCanceladas) {
        this.mes = mes;
        this.totalCanceladas = totalCanceladas;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public long getTotalCanceladas() {
        return totalCanceladas;
    }

    public void setTotalCanceladas(long totalCanceladas) {
        this.totalCanceladas = totalCanceladas;
    }
}

