package com.decolatech.easytravel.common.dashboard.dto;

public class ReservasAtivasPorRankDTO {
    private int bundleRank;
    private long reservasAtivas;

    public ReservasAtivasPorRankDTO(int bundleRank, long reservasAtivas) {
        this.bundleRank = bundleRank;
        this.reservasAtivas = reservasAtivas;
    }

    public int getBundleRank() {
        return bundleRank;
    }

    public void setBundleRank(int bundleRank) {
        this.bundleRank = bundleRank;
    }

    public long getReservasAtivas() {
        return reservasAtivas;
    }

    public void setReservasAtivas(long reservasAtivas) {
        this.reservasAtivas = reservasAtivas;
    }
}

