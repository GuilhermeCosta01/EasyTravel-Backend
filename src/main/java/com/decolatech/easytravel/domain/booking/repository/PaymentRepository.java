package com.decolatech.easytravel.domain.booking.repository;

import com.decolatech.easytravel.domain.booking.entity.Payment;
import com.decolatech.easytravel.domain.booking.enums.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    // Buscar por reserva
    List<Payment> findByReservationId(Integer reservationId);

    // Buscar por método de pagamento
    List<Payment> findByPaymentMethod(PaymentMethod paymentMethod);

    // Buscar por data de pagamento
    @Query("SELECT p FROM Payment p WHERE p.paymentDate BETWEEN :startDate AND :endDate")
    List<Payment> findByPaymentDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Buscar por faixa de valor
    @Query("SELECT p FROM Payment p WHERE p.totPrice BETWEEN :minPrice AND :maxPrice")
    List<Payment> findByPriceRange(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);

    // Buscar pagamentos ordenados por data
    List<Payment> findAllByOrderByPaymentDateDesc();

    // Calcular total de pagamentos por período
    @Query("SELECT SUM(p.totPrice) FROM Payment p WHERE p.paymentDate BETWEEN :startDate AND :endDate")
    Double getTotalPaymentsByPeriod(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Contar pagamentos por método
    @Query("SELECT COUNT(p) FROM Payment p WHERE p.paymentMethod = :method")
    Long countPaymentsByMethod(@Param("method") PaymentMethod method);

    // Vendas por método de pagamento (JPQL)
    @Query("SELECT CAST(p.paymentMethod AS int), COUNT(p), SUM(p.totPrice) FROM Payment p GROUP BY p.paymentMethod")
    List<Object[]> findVendasPorPagamento();

    // Usuários por método de pagamento (JPQL)
    @Query("SELECT CAST(p.paymentMethod AS int), COUNT(DISTINCT r.user.id) FROM Payment p JOIN p.reservation r GROUP BY p.paymentMethod")
    List<Object[]> findUsuariosPorMetodoPagamento();

    // Receita total por mês (JPQL)
    @Query("SELECT FUNCTION('YEAR', p.paymentDate), FUNCTION('MONTH', p.paymentDate), SUM(p.totPrice) FROM Payment p GROUP BY FUNCTION('YEAR', p.paymentDate), FUNCTION('MONTH', p.paymentDate) ORDER BY FUNCTION('YEAR', p.paymentDate), FUNCTION('MONTH', p.paymentDate)")
    List<Object[]> findReceitaPorMes();




}
