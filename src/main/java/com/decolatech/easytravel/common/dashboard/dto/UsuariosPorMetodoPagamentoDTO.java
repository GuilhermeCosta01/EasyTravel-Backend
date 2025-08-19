package com.decolatech.easytravel.common.dashboard.dto;

public class UsuariosPorMetodoPagamentoDTO {
    private int paymentMethod;
    private long totalUsuarios;

    public UsuariosPorMetodoPagamentoDTO(int paymentMethod, long totalUsuarios) {
        this.paymentMethod = paymentMethod;
        this.totalUsuarios = totalUsuarios;
    }

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public long getTotalUsuarios() {
        return totalUsuarios;
    }

    public void setTotalUsuarios(long totalUsuarios) {
        this.totalUsuarios = totalUsuarios;
    }
}

