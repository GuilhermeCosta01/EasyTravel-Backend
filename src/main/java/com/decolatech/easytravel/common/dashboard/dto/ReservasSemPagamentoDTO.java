package com.decolatech.easytravel.common.dashboard.dto;

public class ReservasSemPagamentoDTO {
    private long reservasSemPagamento;

    public ReservasSemPagamentoDTO(long reservasSemPagamento) {
        this.reservasSemPagamento = reservasSemPagamento;
    }

    public long getReservasSemPagamento() {
        return reservasSemPagamento;
    }

    public void setReservasSemPagamento(long reservasSemPagamento) {
        this.reservasSemPagamento = reservasSemPagamento;
    }
}

