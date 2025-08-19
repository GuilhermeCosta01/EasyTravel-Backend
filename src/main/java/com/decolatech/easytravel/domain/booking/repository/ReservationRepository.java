package com.decolatech.easytravel.domain.booking.repository;

import com.decolatech.easytravel.domain.booking.entity.Reservation;
import com.decolatech.easytravel.domain.booking.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    // Buscar por usuário
    List<Reservation> findByUserId(Integer userId);

    // Buscar por bundle
    List<Reservation> findByBundleId(Integer bundleId);

    // Buscar por status
    List<Reservation> findByReservStatus(ReservationStatus status);

    // Buscar por usuário e status
    List<Reservation> findByUserIdAndReservStatus(Integer userId, ReservationStatus status);

    // Buscar reservas por período
    @Query("SELECT r FROM Reservation r WHERE r.reservDate BETWEEN :startDate AND :endDate")
    List<Reservation> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Buscar reservas ativas de um usuário
    @Query("SELECT r FROM Reservation r WHERE r.user.id = :userId AND r.reservStatus IN (:statuses)")
    List<Reservation> findActiveReservationsByUser(@Param("userId") Integer userId, @Param("statuses") List<ReservationStatus> statuses);

    // Contar reservas por bundle
    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.bundle.id = :bundleId")
    Long countReservationsByBundle(@Param("bundleId") Integer bundleId);

    // Verificar se usuário já tem reserva para um bundle
    boolean existsByUserIdAndBundleId(Integer userId, Integer bundleId);

    // Reservas ativas por rank de pacote (JPQL)
    @Query("SELECT CAST(b.bundleRank AS int), COUNT(r) FROM Reservation r JOIN r.bundle b WHERE r.reservStatus = 0 GROUP BY b.bundleRank")
    List<Object[]> findReservasAtivasPorRank();

    // Reservas canceladas por mês (JPQL)
    @Query("SELECT FUNCTION('YEAR', r.reservDate), FUNCTION('MONTH', r.reservDate), COUNT(r) FROM Reservation r WHERE r.reservStatus = 1 GROUP BY FUNCTION('YEAR', r.reservDate), FUNCTION('MONTH', r.reservDate) ORDER BY FUNCTION('YEAR', r.reservDate), FUNCTION('MONTH', r.reservDate)")
    List<Object[]> findReservasCanceladasPorMes();

    // Total de reservas por pacote (JPQL)
    @Query("SELECT b.id, COUNT(r) FROM Bundle b LEFT JOIN b.reservations r GROUP BY b.id")
    List<Object[]> findTotalReservasPorPacote();

    // Total de reservas sem pagamentos efetivados (JPQL corrigido)
    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.id NOT IN (SELECT p.reservation.id FROM Payment p)")
    Long findReservasSemPagamento();


}
