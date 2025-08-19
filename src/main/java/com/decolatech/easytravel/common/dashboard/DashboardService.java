package com.decolatech.easytravel.common.dashboard;

import com.decolatech.easytravel.common.dashboard.dto.*;
import com.decolatech.easytravel.domain.booking.repository.PaymentRepository;
import com.decolatech.easytravel.domain.booking.repository.ReservationRepository;
import com.decolatech.easytravel.domain.bundle.repository.BundleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private BundleRepository bundleRepository;

    public List<ReservasAtivasPorRankDTO> getReservasAtivasPorRank() {
        return reservationRepository.findReservasAtivasPorRank().stream()
            .map(obj -> new ReservasAtivasPorRankDTO(
                (Integer) obj[0],
                (Long) obj[1]
            )).collect(Collectors.toList());
    }

    public List<ReservasCanceladasPorMesDTO> getReservasCanceladasPorMes() {
        return reservationRepository.findReservasCanceladasPorMes().stream()
            .map(obj -> {
                int year = (Integer) obj[0];
                int month = (Integer) obj[1];
                String mes = String.format("%04d-%02d", year, month);
                return new ReservasCanceladasPorMesDTO(mes, (Long) obj[2]);
            }).collect(Collectors.toList());
    }

    public List<TotalReservasPorPacoteDTO> getTotalReservasPorPacote() {
        return reservationRepository.findTotalReservasPorPacote().stream()
            .map(obj -> new TotalReservasPorPacoteDTO(
                (Integer) obj[0],
                (Long) obj[1]
            )).collect(Collectors.toList());
    }

    public List<VendasPorPagamentoDTO> getVendasPorPagamento() {
        return paymentRepository.findVendasPorPagamento().stream()
            .map(obj -> new VendasPorPagamentoDTO(
                (Integer) obj[0],
                (Long) obj[1],
                obj[2] != null ? ((Number) obj[2]).doubleValue() : 0.0
            )).collect(Collectors.toList());
    }

    public List<UsuariosPorMetodoPagamentoDTO> getUsuariosPorMetodoPagamento() {
        return paymentRepository.findUsuariosPorMetodoPagamento().stream()
            .map(obj -> new UsuariosPorMetodoPagamentoDTO(
                (Integer) obj[0],
                (Long) obj[1]
            )).collect(Collectors.toList());
    }

    public List<ReceitaPorMesDTO> getReceitaPorMes() {
        return paymentRepository.findReceitaPorMes().stream()
            .map(obj -> {
                int year = (Integer) obj[0];
                int month = (Integer) obj[1];
                String mes = String.format("%04d-%02d", year, month);
                return new ReceitaPorMesDTO(mes, obj[2] != null ? ((Number) obj[2]).doubleValue() : 0.0);
            }).collect(Collectors.toList());
    }

    public List<FaturamentoPorPacoteDTO> getFaturamentoPorPacote() {
        return bundleRepository.findFaturamentoPorPacote().stream()
            .map(obj -> new FaturamentoPorPacoteDTO(
                (Integer) obj[0],
                obj[1] != null ? ((Number) obj[1]).doubleValue() : 0.0
            )).collect(Collectors.toList());
    }

    public List<VendasPorCidadeDTO> getVendasPorCidade() {
        return bundleRepository.findVendasPorCidade().stream()
            .map(obj -> new VendasPorCidadeDTO(
                (String) obj[0],
                obj[1] != null ? ((Number) obj[1]).doubleValue() : 0.0
            )).collect(Collectors.toList());
    }

    public ReservasSemPagamentoDTO getReservasSemPagamento() {
        Long total = reservationRepository.findReservasSemPagamento();
        return new ReservasSemPagamentoDTO(total != null ? total : 0L);
    }
}
