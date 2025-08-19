package com.decolatech.easytravel.domain.booking.service;

import com.decolatech.easytravel.domain.booking.dto.ReservationDTO;
import com.decolatech.easytravel.domain.booking.entity.Reservation;
import com.decolatech.easytravel.domain.booking.enums.ReservationStatus;
import com.decolatech.easytravel.domain.booking.repository.ReservationRepository;
import com.decolatech.easytravel.domain.bundle.dto.BundleDTO;
import com.decolatech.easytravel.domain.bundle.entity.Bundle;
import com.decolatech.easytravel.domain.bundle.repository.BundleRepository;
import com.decolatech.easytravel.domain.user.entity.User;
import com.decolatech.easytravel.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BundleRepository bundleRepository;

    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ReservationDTO getReservationById(Integer id) {
        return reservationRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
    }

    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        User user = userRepository.findById(reservationDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Bundle bundle = bundleRepository.findById(reservationDTO.getBundleId())
                .orElseThrow(() -> new RuntimeException("Pacote não encontrado"));

        // Verificar se usuário já tem reserva para este bundle
        if (reservationRepository.existsByUserIdAndBundleId(reservationDTO.getUserId(), reservationDTO.getBundleId())) {
            throw new IllegalArgumentException("Usuário já possui reserva para este pacote");
        }

        Reservation reservation = convertToEntity(reservationDTO);
        reservation.setUser(user);
        reservation.setBundle(bundle);
        reservation.setReservDate(LocalDateTime.now());
        reservation.setReservStatus(ReservationStatus.PENDING);

        Reservation savedReservation = reservationRepository.save(reservation);
        return convertToDTO(savedReservation);
    }

    public ReservationDTO updateReservation(Integer id, ReservationDTO reservationDTO) {
        Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));

        User user = userRepository.findById(reservationDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Bundle bundle = bundleRepository.findById(reservationDTO.getBundleId())
                .orElseThrow(() -> new RuntimeException("Pacote não encontrado"));

        boolean updated = false;
        if (!existingReservation.getUser().getId().equals(user.getId())) updated = true;
        if (!existingReservation.getBundle().getId().equals(bundle.getId())) updated = true;
        if (!existingReservation.getReservStatus().equals(reservationDTO.getReservStatus())) updated = true;

        if (!updated) {
            throw new IllegalArgumentException("Nenhuma alteração foi detectada nos dados da reserva");
        }

        existingReservation.setUser(user);
        existingReservation.setBundle(bundle);
        existingReservation.setReservStatus(reservationDTO.getReservStatus());

        Reservation savedReservation = reservationRepository.save(existingReservation);
        return convertToDTO(savedReservation);
    }

    public void deleteReservation(Integer id) {
        if (!reservationRepository.existsById(id)) {
            throw new RuntimeException("Reserva não encontrada");
        }
        reservationRepository.deleteById(id);
    }

    public List<ReservationDTO> getReservationsByUser(Integer userId) {
        List<ReservationDTO> reservations = reservationRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        if (reservations.isEmpty()) {
            throw new IllegalArgumentException("Nenhuma reserva encontrada para o usuário especificado");
        }

        return reservations;
    }

    public List<ReservationDTO> getReservationsByStatus(ReservationStatus status) {
        List<ReservationDTO> reservations = reservationRepository.findByReservStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        if (reservations.isEmpty()) {
            throw new IllegalArgumentException("Nenhuma reserva encontrada com o status especificado");
        }

        return reservations;
    }

    public ReservationDTO confirmReservation(Integer id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));

        reservation.setReservStatus(ReservationStatus.CONFIRMED);
        Reservation savedReservation = reservationRepository.save(reservation);
        return convertToDTO(savedReservation);
    }

    public ReservationDTO cancelReservation(Integer id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));

        reservation.setReservStatus(ReservationStatus.CANCELLED);
        Reservation savedReservation = reservationRepository.save(reservation);
        return convertToDTO(savedReservation);
    }

    private ReservationDTO convertToDTO(Reservation reservation) {
        ReservationDTO dto = new ReservationDTO();
        dto.setId(reservation.getId());
        dto.setReservStatus(reservation.getReservStatus());
        dto.setReservDate(reservation.getReservDate());
        dto.setUserId(reservation.getUser().getId());
        dto.setBundleId(reservation.getBundle().getId());
        dto.setUserName(reservation.getUser().getUsername());
        dto.setBundleTitle(reservation.getBundle().getBundleTitle());
        // Preencher o campo bundle com todas as informações do pacote
        Bundle bundle = reservation.getBundle();
        if (bundle != null) {
            BundleDTO bundleDTO = new BundleDTO();
            bundleDTO.setId(bundle.getId());
            bundleDTO.setBundleTitle(bundle.getBundleTitle());
            bundleDTO.setBundleDescription(bundle.getBundleDescription());
            bundleDTO.setInitialPrice(bundle.getInitialPrice());
            bundleDTO.setBundleRank(bundle.getBundleRank());
            bundleDTO.setInitialDate(bundle.getInitialDate());
            bundleDTO.setFinalDate(bundle.getFinalDate());
            bundleDTO.setQuantity(bundle.getQuantity());
            bundleDTO.setTravelersNumber(bundle.getTravelersNumber());
            bundleDTO.setBundleStatus(bundle.getBundleStatus());
            dto.setBundle(bundleDTO);
        }
        return dto;
    }

    private Reservation convertToEntity(ReservationDTO dto) {
        Reservation reservation = new Reservation();
        reservation.setId(dto.getId());
        reservation.setReservStatus(dto.getReservStatus());
        reservation.setReservDate(dto.getReservDate());
        return reservation;
    }
}
