package com.decolatech.easytravel.domain.booking.service;

import com.decolatech.easytravel.domain.booking.dto.TravelerDTO;
import com.decolatech.easytravel.domain.booking.entity.Traveler;
import com.decolatech.easytravel.domain.booking.entity.Reservation;
import com.decolatech.easytravel.domain.booking.repository.TravelerRepository;
import com.decolatech.easytravel.domain.booking.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TravelerService {

    @Autowired
    private TravelerRepository travelerRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public List<TravelerDTO> getAllTravelers() {
        return travelerRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TravelerDTO getTravelerById(Integer id) {
        return travelerRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Viajante não encontrado"));
    }

    public TravelerDTO createTraveler(TravelerDTO travelerDTO) {
        if (travelerDTO.getReservationId() == null) {
            throw new RuntimeException("ID da reserva é obrigatório");
        }
        Reservation reservation = reservationRepository.findById(travelerDTO.getReservationId())
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
        // Verifica se já existe viajante com o mesmo tipo e número de documento na mesma reserva
        Traveler existing = travelerRepository.findOneByDocumentTypeAndDocumentNumberAndReservationId(
            travelerDTO.getDocumentType(),
            travelerDTO.getDocumentNumber(),
            travelerDTO.getReservationId()
        ).orElse(null);
        if (existing != null) {
            throw new RuntimeException("Já existe um viajante com este tipo e número de documento nesta reserva");
        }
        Traveler traveler = convertToEntity(travelerDTO);
        traveler.setReservation(reservation);
        Traveler savedTraveler = travelerRepository.save(traveler);
        return convertToDTO(savedTraveler);
    }

    public TravelerDTO updateTraveler(Integer id, TravelerDTO travelerDTO, String usernameOrEmail) {
        Traveler existingTraveler = travelerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Viajante não encontrado"));
        Reservation reservation = existingTraveler.getReservation();
        if (reservation == null || reservation.getUser() == null ||
            !(usernameOrEmail.equalsIgnoreCase(reservation.getUser().getEmail()) ||
              usernameOrEmail.equalsIgnoreCase(reservation.getUser().getName()))) {
            throw new SecurityException("Acesso negado: você só pode alterar viajantes das suas próprias reservas");
        }
        boolean updated = false;
        if (travelerDTO.getFullName() != null && !travelerDTO.getFullName().equals(existingTraveler.getFullName())) {
            existingTraveler.setFullName(travelerDTO.getFullName());
            updated = true;
        }
        if (travelerDTO.getDocumentNumber() != null && !travelerDTO.getDocumentNumber().equals(existingTraveler.getDocumentNumber())) {
            Traveler other = travelerRepository.findOneByDocumentTypeAndDocumentNumberAndReservationId(
                travelerDTO.getDocumentType() != null ? travelerDTO.getDocumentType() : existingTraveler.getDocumentType(),
                travelerDTO.getDocumentNumber(),
                (travelerDTO.getReservationId() != null) ? travelerDTO.getReservationId() : existingTraveler.getReservation().getId()
            ).orElse(null);
            if (other != null && !other.getId().equals(existingTraveler.getId())) {
                throw new RuntimeException("Já existe um viajante com este tipo e número de documento nesta reserva");
            }
            existingTraveler.setDocumentNumber(travelerDTO.getDocumentNumber());
            if (travelerDTO.getDocumentType() != null) {
                existingTraveler.setDocumentType(travelerDTO.getDocumentType());
            }
            updated = true;
        } else if (travelerDTO.getDocumentType() != null && !travelerDTO.getDocumentType().equals(existingTraveler.getDocumentType())) {
            Traveler other = travelerRepository.findOneByDocumentTypeAndDocumentNumberAndReservationId(
                travelerDTO.getDocumentType(),
                existingTraveler.getDocumentNumber(),
                (travelerDTO.getReservationId() != null) ? travelerDTO.getReservationId() : existingTraveler.getReservation().getId()
            ).orElse(null);
            if (other != null && !other.getId().equals(existingTraveler.getId())) {
                throw new RuntimeException("Já existe um viajante com este tipo e número de documento nesta reserva");
            }
            existingTraveler.setDocumentType(travelerDTO.getDocumentType());
            updated = true;
        }
        if (travelerDTO.getAge() != null && !travelerDTO.getAge().equals(existingTraveler.getAge())) {
            existingTraveler.setAge(travelerDTO.getAge());
            updated = true;
        }
        // Só atualiza reservation se vier no JSON e for diferente
        if (travelerDTO.getReservationId() != null &&
            (existingTraveler.getReservation() == null || !existingTraveler.getReservation().getId().equals(travelerDTO.getReservationId()))) {
            Reservation newReservation = reservationRepository.findById(travelerDTO.getReservationId())
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
            if (newReservation.getUser() == null ||
                !(usernameOrEmail.equalsIgnoreCase(newReservation.getUser().getEmail()) ||
                  usernameOrEmail.equalsIgnoreCase(newReservation.getUser().getName()))) {
                throw new SecurityException("Acesso negado: só pode mover viajante para reserva própria");
            }
            existingTraveler.setReservation(newReservation);
            updated = true;
        }
        if (!updated) {
            throw new IllegalArgumentException("Nenhuma alteração foi detectada no viajante");
        }
        Traveler savedTraveler = travelerRepository.save(existingTraveler);
        return convertToDTO(savedTraveler);
    }

    public void deleteTraveler(Integer id) {
        if (!travelerRepository.existsById(id)) {
            throw new RuntimeException("Viajante não encontrado");
        }
        travelerRepository.deleteById(id);
    }

    public List<TravelerDTO> getTravelersByReservation(Integer reservationId) {
        if (reservationId == null) {
            throw new IllegalArgumentException("ID da reserva não pode ser nulo");
        }
        return travelerRepository.findByReservationId(reservationId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TravelerDTO> searchTravelersByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }
        return travelerRepository.findByFullNameContainingIgnoreCase(name.trim()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TravelerDTO> getTravelersByReservationAndUser(Integer reservationId, String usernameOrEmail) {
        if (reservationId == null) {
            throw new IllegalArgumentException("ID da reserva não pode ser nulo");
        }
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reserva não encontrada"));
        // Verifica se o usuário autenticado é o dono da reserva (por e-mail ou username)
        if (reservation.getUser() == null ||
            !(usernameOrEmail.equalsIgnoreCase(reservation.getUser().getEmail()) ||
              usernameOrEmail.equalsIgnoreCase(reservation.getUser().getName()))) {
            throw new SecurityException("Acesso negado: reserva não pertence ao usuário autenticado");
        }
        return travelerRepository.findByReservationId(reservationId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private TravelerDTO convertToDTO(Traveler traveler) {
        TravelerDTO dto = new TravelerDTO();
        dto.setId(traveler.getId());
        dto.setFullName(traveler.getFullName());
        dto.setDocumentNumber(traveler.getDocumentNumber());
        dto.setDocumentType(traveler.getDocumentType());
        dto.setAge(traveler.getAge());
        dto.setReservationId(traveler.getReservation() != null ? traveler.getReservation().getId() : null);
        return dto;
    }

    private Traveler convertToEntity(TravelerDTO dto) {
        Traveler traveler = new Traveler();
        traveler.setId(dto.getId());
        traveler.setFullName(dto.getFullName());
        traveler.setDocumentNumber(dto.getDocumentNumber());
        traveler.setDocumentType(dto.getDocumentType());
        traveler.setAge(dto.getAge());
        return traveler;
    }
}
