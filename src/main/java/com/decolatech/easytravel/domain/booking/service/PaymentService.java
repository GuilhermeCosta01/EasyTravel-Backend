package com.decolatech.easytravel.domain.booking.service;

import com.decolatech.easytravel.common.EmailService;
import com.decolatech.easytravel.common.PricingService;
import com.decolatech.easytravel.common.PaymentReceiptPdfGenerator;
import com.decolatech.easytravel.domain.booking.dto.PaymentDTO;
import com.decolatech.easytravel.domain.booking.entity.Payment;
import com.decolatech.easytravel.domain.booking.entity.Reservation;
import com.decolatech.easytravel.domain.booking.entity.TravelHistory;
import com.decolatech.easytravel.domain.booking.enums.PaymentMethod;
import com.decolatech.easytravel.domain.booking.repository.PaymentRepository;
import com.decolatech.easytravel.domain.booking.repository.ReservationRepository;
import com.decolatech.easytravel.domain.booking.repository.TravelHistoryRepository;
import com.decolatech.easytravel.domain.bundle.entity.Bundle;
import com.decolatech.easytravel.domain.user.entity.User;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.DocumentException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PricingService pricingService;

    @Autowired
    private EmailService emailService;
    @Autowired
    private PaymentReceiptPdfGenerator paymentReceiptPdfGenerator;

    @Autowired
    private TravelHistoryRepository travelHistoryRepository;

    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PaymentDTO getPaymentById(Integer id) {
        return paymentRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
    }

    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        // Validar se o totPrice foi informado
        if (paymentDTO.getTotPrice() == null || paymentDTO.getTotPrice() <= 0) {
            throw new IllegalArgumentException("O valor total do pacote é obrigatório e deve ser maior que zero");
        }

        Reservation reservation = reservationRepository.findById(paymentDTO.getReservationId())
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));

        // Verifica se já existe pagamento para esta reserva
        List<Payment> pagamentos = paymentRepository.findByReservationId(paymentDTO.getReservationId());
        if (!pagamentos.isEmpty()) {
            throw new com.decolatech.easytravel.domain.booking.exception.ReservationAlreadyPaidException(
                "Esta reserva já foi paga e não pode ser paga novamente.");
        }

        Payment payment = convertToEntity(paymentDTO);
        payment.setReservation(reservation);

        // Só permite informar installments se for CREDIT, senão zera o campo
        if (payment.getPaymentMethod() != null && payment.getPaymentMethod().name().equals("CREDIT")) {
            payment.setInstallments(paymentDTO.getInstallments());
        } else {
            payment.setInstallments(null);
        }

        // Aplicar desconto no valor total recebido baseado no método de pagamento
        Double originalValue = paymentDTO.getTotPrice();
        Double finalValue = pricingService.applyPaymentDiscount(originalValue, payment.getPaymentMethod(), payment.getInstallments());
        payment.setTotPrice(finalValue);

        payment.setPaymentDate(LocalDateTime.now());

        Payment savedPayment = paymentRepository.save(payment);
        // Atualiza o status da reserva para CONFIRMED após o pagamento
        reservation.setReservStatus(com.decolatech.easytravel.domain.booking.enums.ReservationStatus.CONFIRMED);
        reservationRepository.save(reservation);
        // Envia comprovante em PDF por e-mail
        String pdfFileName = "comprovante_pagamento_" + savedPayment.getId() + ".pdf";
        try {
            User user = reservation.getUser();
            byte[] pdf = paymentReceiptPdfGenerator.generate(savedPayment);
            String subject = "Comprovante de Pagamento - EasyTravel";
            String text = "Olá, " + user.getName() + "!\nSegue em anexo o comprovante do seu pagamento (ID: " + savedPayment.getId() + ").";
            emailService.sendEmailWithAttachment(user.getEmail(), subject, text, pdf, pdfFileName);
            // Salva TravelHistory apenas se não existir para este pagamento
            boolean alreadyExists = !travelHistoryRepository.findByPaymentId(savedPayment.getId()).isEmpty();
            if (!alreadyExists) {
                TravelHistory travelHistory = new TravelHistory();
                travelHistory.setPayment(savedPayment);
                travelHistory.setPdf(pdfFileName);
                travelHistoryRepository.save(travelHistory);
            }
        } catch (DocumentException | IOException | jakarta.mail.MessagingException e) {
            // Logar erro, mas não impedir o fluxo do pagamento
            e.printStackTrace();
        }
        return convertToDTO(savedPayment);
    }

    public PaymentDTO updatePayment(Integer id, PaymentDTO paymentDTO) {
        Payment existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));

        Reservation reservation = reservationRepository.findById(paymentDTO.getReservationId())
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));

        boolean updated = false;
        if (!existingPayment.getPaymentMethod().equals(paymentDTO.getPaymentMethod())) updated = true;
        if (!existingPayment.getTotPrice().equals(paymentDTO.getTotPrice())) updated = true;
        if (!existingPayment.getReservation().getId().equals(reservation.getId())) updated = true;

        if (!updated) {
            throw new IllegalArgumentException("Nenhuma alteração foi detectada nos dados do pagamento");
        }

        existingPayment.setPaymentMethod(paymentDTO.getPaymentMethod());
        existingPayment.setTotPrice(paymentDTO.getTotPrice());
        existingPayment.setReservation(reservation);

        Payment savedPayment = paymentRepository.save(existingPayment);
        return convertToDTO(savedPayment);
    }

    public void deletePayment(Integer id) {
        if (!paymentRepository.existsById(id)) {
            throw new RuntimeException("Pagamento não encontrado");
        }
        paymentRepository.deleteById(id);
    }

    public List<PaymentDTO> getPaymentsByReservation(Integer reservationId) {
        return paymentRepository.findByReservationId(reservationId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<PaymentDTO> getPaymentsByMethod(PaymentMethod method) {
        return paymentRepository.findByPaymentMethod(method).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<PaymentDTO> getPaymentsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return paymentRepository.findByPaymentDateRange(startDate, endDate).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Double getTotalPaymentsByPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        return paymentRepository.getTotalPaymentsByPeriod(startDate, endDate);
    }

    private PaymentDTO convertToDTO(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setTotPrice(payment.getTotPrice());
        dto.setReservationId(payment.getReservation().getId());
        return dto;
    }

    private Payment convertToEntity(PaymentDTO dto) {
        Payment payment = new Payment();
        payment.setId(dto.getId());
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setTotPrice(dto.getTotPrice());
        payment.setInstallments(dto.getInstallments()); // Adicionar installments na conversão
        return payment;
    }
}
