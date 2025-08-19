package com.decolatech.easytravel.domain.booking.repository;

import com.decolatech.easytravel.domain.booking.entity.Review;
import com.decolatech.easytravel.domain.booking.enums.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    // Buscar por avaliação
    List<Review> findByRating(Rating rating);

    // Buscar por data de avaliação
    @Query("SELECT r FROM Review r WHERE r.avaliationDate BETWEEN :startDate AND :endDate")
    List<Review> findByAvaliationDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Buscar reviews com comentário
    List<Review> findByCommentIsNotNull();

    // Buscar reviews por rating mínimo
    @Query("SELECT r FROM Review r WHERE r.rating >= :minRating")
    List<Review> findByMinimumRating(@Param("minRating") Rating minRating);

    // Buscar reviews ordenadas por data
    List<Review> findAllByOrderByAvaliationDateDesc();

    // Calcular média de avaliações
    @Query("SELECT AVG(CAST(r.rating AS double)) FROM Review r")
    Double getAverageRating();

    // Contar reviews por rating
    @Query("SELECT COUNT(r) FROM Review r WHERE r.rating = :rating")
    Long countReviewsByRating(@Param("rating") Rating rating);

    // Buscar avaliações pelo id do usuário
    @Query("SELECT r FROM Review r WHERE r.travelHistory.payment.reservation.user.id = :userId")
    List<Review> findByUserId(@Param("userId") Integer userId);

    // Buscar review por id do histórico de viagem
    List<Review> findByTravelHistoryId(Integer travelHistoryId);

    // Buscar review por id e id do usuário (para update seguro)
    @Query("SELECT r FROM Review r WHERE r.id = :reviewId AND r.travelHistory.payment.reservation.user.id = :userId")
    Review findByIdAndUserId(@Param("reviewId") Integer reviewId, @Param("userId") Integer userId);

    // Buscar reviews por ID do bundle/pacote
    @Query("SELECT r FROM Review r WHERE r.bundle.id = :bundleId")
    List<Review> findByBundleId(@Param("bundleId") Integer bundleId);
}
