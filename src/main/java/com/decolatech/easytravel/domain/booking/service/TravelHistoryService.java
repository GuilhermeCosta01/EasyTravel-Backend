package com.decolatech.easytravel.domain.booking.service;

import com.decolatech.easytravel.domain.booking.dto.TravelHistoryDTO;
import com.decolatech.easytravel.domain.booking.dto.PaymentDTO;
import com.decolatech.easytravel.domain.booking.entity.TravelHistory;
import com.decolatech.easytravel.domain.booking.entity.Payment;
import com.decolatech.easytravel.domain.booking.repository.TravelHistoryRepository;
import com.decolatech.easytravel.domain.booking.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TravelHistoryService {

    @Autowired
    private TravelHistoryRepository travelHistoryRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    public List<TravelHistoryDTO> getAllTravelHistories() {
        return travelHistoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TravelHistoryDTO getTravelHistoryById(Integer id) {
        return travelHistoryRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Histórico de viagem não encontrado"));
    }

    public TravelHistoryDTO createTravelHistory(TravelHistoryDTO travelHistoryDTO) {
        if (travelHistoryDTO.getPaymentId() == null) {
            throw new RuntimeException("ID do pagamento é obrigatório");
        }

        Payment payment = paymentRepository.findById(travelHistoryDTO.getPaymentId())
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));

        TravelHistory travelHistory = new TravelHistory();
        travelHistory.setPayment(payment);
        TravelHistory saved = travelHistoryRepository.save(travelHistory);
        return convertToDTO(saved);
    }

    public TravelHistoryDTO updateTravelHistory(Integer id, TravelHistoryDTO travelHistoryDTO) {
        TravelHistory existingTravelHistory = travelHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Histórico de viagem não encontrado"));

        boolean updated = false;

        if (travelHistoryDTO.getPaymentId() != null &&
            !existingTravelHistory.getPayment().getId().equals(travelHistoryDTO.getPaymentId())) {
            Payment payment = paymentRepository.findById(travelHistoryDTO.getPaymentId())
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
            existingTravelHistory.setPayment(payment);
            updated = true;
        }

        if (!updated) {
            throw new IllegalArgumentException("Nenhuma alteração foi detectada no histórico de viagem");
        }

        TravelHistory savedTravelHistory = travelHistoryRepository.save(existingTravelHistory);
        return convertToDTO(savedTravelHistory);
    }

    public void deleteTravelHistory(Integer id) {
        if (!travelHistoryRepository.existsById(id)) {
            throw new RuntimeException("Histórico de viagem não encontrado");
        }
        travelHistoryRepository.deleteById(id);
    }

    public List<TravelHistoryDTO> getTravelHistoriesByPayment(Integer paymentId) {
        if (paymentId == null) {
            throw new IllegalArgumentException("ID do pagamento não pode ser nulo");
        }
        return travelHistoryRepository.findByPaymentId(paymentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TravelHistoryDTO> getTravelHistoriesByUser(Integer userId) {
        return travelHistoryRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    private TravelHistoryDTO convertToDTO(TravelHistory travelHistory) {
        TravelHistoryDTO dto = new TravelHistoryDTO();
        dto.setId(travelHistory.getId());
        dto.setPaymentId(travelHistory.getPayment() != null ? travelHistory.getPayment().getId() : null);
        if (travelHistory.getPayment() != null) {
            Payment payment = travelHistory.getPayment();
            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setId(payment.getId());
            paymentDTO.setPaymentDate(payment.getPaymentDate());
            paymentDTO.setPaymentMethod(payment.getPaymentMethod());
            paymentDTO.setTotPrice(payment.getTotPrice());
            paymentDTO.setReservationId(payment.getReservation() != null ? payment.getReservation().getId() : null);
            dto.setPayment(paymentDTO);
        }
        return dto;
    }
}
