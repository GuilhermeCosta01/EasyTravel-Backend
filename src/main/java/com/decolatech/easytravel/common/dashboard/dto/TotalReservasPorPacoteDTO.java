package com.decolatech.easytravel.common.dashboard.dto;

public class TotalReservasPorPacoteDTO {
    private int pacoteId;
    private long totalReservas;

    public TotalReservasPorPacoteDTO(int pacoteId, long totalReservas) {
        this.pacoteId = pacoteId;
        this.totalReservas = totalReservas;
    }

    public int getPacoteId() {
        return pacoteId;
    }

    public void setPacoteId(int pacoteId) {
        this.pacoteId = pacoteId;
    }

    public long getTotalReservas() {
        return totalReservas;
    }

    public void setTotalReservas(long totalReservas) {
        this.totalReservas = totalReservas;
    }
}

