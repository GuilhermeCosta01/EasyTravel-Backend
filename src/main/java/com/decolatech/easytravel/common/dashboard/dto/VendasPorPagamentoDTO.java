package com.decolatech.easytravel.common.dashboard.dto;

public class VendasPorPagamentoDTO {
    private int paymentMethod;
    private long totalTransactions;
    private double totalRevenue;

    public VendasPorPagamentoDTO(int paymentMethod, long totalTransactions, double totalRevenue) {
        this.paymentMethod = paymentMethod;
        this.totalTransactions = totalTransactions;
        this.totalRevenue = totalRevenue;
    }

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public long getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(long totalTransactions) {
        this.totalTransactions = totalTransactions;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}

