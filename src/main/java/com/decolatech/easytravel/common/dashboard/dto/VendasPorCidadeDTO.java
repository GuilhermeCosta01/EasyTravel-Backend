package com.decolatech.easytravel.common.dashboard.dto;

public class VendasPorCidadeDTO {
    private String destination;
    private double totalVendas;

    public VendasPorCidadeDTO(String destination, double totalVendas) {
        this.destination = destination;
        this.totalVendas = totalVendas;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getTotalVendas() {
        return totalVendas;
    }

    public void setTotalVendas(double totalVendas) {
        this.totalVendas = totalVendas;
    }
}

