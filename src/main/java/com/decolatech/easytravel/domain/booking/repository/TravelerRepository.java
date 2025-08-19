package com.decolatech.easytravel.domain.booking.repository;

import com.decolatech.easytravel.domain.booking.entity.Traveler;
import com.decolatech.easytravel.domain.booking.enums.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TravelerRepository extends JpaRepository<Traveler, Integer> {

    // Buscar por reserva
    List<Traveler> findByReservationId(Integer reservationId);

    // Buscar por documento
    Optional<Traveler> findByDocumentNumber(String documentNumber);

    // Buscar por tipo de documento
    List<Traveler> findByDocumentType(DocumentType documentType);

    // Buscar por nome
    List<Traveler> findByFullNameContainingIgnoreCase(String fullName);

    // Buscar por idade
    List<Traveler> findByAge(Integer age);

    // Buscar por faixa etária
    @Query("SELECT t FROM Traveler t WHERE t.age BETWEEN :minAge AND :maxAge")
    List<Traveler> findByAgeRange(@Param("minAge") Integer minAge, @Param("maxAge") Integer maxAge);

    // Verificar se documento já existe
    boolean existsByDocumentNumber(String documentNumber);

    // Verificar se já existe viajante com o mesmo tipo e número de documento
    boolean existsByDocumentTypeAndDocumentNumber(com.decolatech.easytravel.domain.booking.enums.DocumentType documentType, String documentNumber);

    // Contar viajantes por reserva
    @Query("SELECT COUNT(t) FROM Traveler t WHERE t.reservation.id = :reservationId")
    Long countTravelersByReservation(@Param("reservationId") Integer reservationId);

    // Buscar viajante por tipo e número de documento (retorna Optional)
    Optional<Traveler> findOneByDocumentTypeAndDocumentNumber(com.decolatech.easytravel.domain.booking.enums.DocumentType documentType, String documentNumber);

    // Buscar viajante por tipo, número de documento e reserva (único na reserva)
    Optional<Traveler> findOneByDocumentTypeAndDocumentNumberAndReservationId(
        com.decolatech.easytravel.domain.booking.enums.DocumentType documentType,
        String documentNumber,
        Integer reservationId
    );
}
