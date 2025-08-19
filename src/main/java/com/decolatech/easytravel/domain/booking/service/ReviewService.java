package com.decolatech.easytravel.domain.booking.service;

import com.decolatech.easytravel.domain.booking.dto.ReviewDTO;
import com.decolatech.easytravel.domain.booking.entity.Review;
import com.decolatech.easytravel.domain.booking.entity.TravelHistory;
import com.decolatech.easytravel.domain.booking.enums.Rating;
import com.decolatech.easytravel.domain.booking.repository.ReviewRepository;
import com.decolatech.easytravel.domain.booking.repository.TravelHistoryRepository;
import com.decolatech.easytravel.domain.bundle.entity.Bundle;
import com.decolatech.easytravel.domain.bundle.repository.BundleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private TravelHistoryRepository travelHistoryRepository;

    @Autowired
    private BundleRepository bundleRepository;

    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ReviewDTO getReviewById(Integer id) {
        return reviewRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));
    }

    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        // Verifica se já existe review para o mesmo histórico de viagem
        if (reviewDTO.getTravelHistoryId() != null &&
            !reviewRepository.findByTravelHistoryId(reviewDTO.getTravelHistoryId()).isEmpty()) {
            throw new com.decolatech.easytravel.domain.booking.exception.OneReviewPerTravelHistoryException(
                "Já existe um comentário para este histórico de viagem.");
        }
        Review review = convertToEntity(reviewDTO);
        review.setAvaliationDate(LocalDateTime.now());

        Review savedReview = reviewRepository.save(review);
        return convertToDTO(savedReview);
    }

    public ReviewDTO updateReview(Integer id, ReviewDTO reviewDTO) {
        Review existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));

        boolean updated = false;

        if (reviewDTO.getRating() != null && !existingReview.getRating().equals(reviewDTO.getRating())) {
            existingReview.setRating(reviewDTO.getRating());
            updated = true;
        }

        if (reviewDTO.getComment() != null && !reviewDTO.getComment().equals(existingReview.getComment())) {
            existingReview.setComment(reviewDTO.getComment());
            updated = true;
        }

        if (reviewDTO.getTravelHistoryId() != null &&
            !existingReview.getTravelHistory().getId().equals(reviewDTO.getTravelHistoryId())) {
            TravelHistory travelHistory = travelHistoryRepository.findById(reviewDTO.getTravelHistoryId())
                .orElseThrow(() -> new RuntimeException("Histórico de viagem não encontrado"));
            existingReview.setTravelHistory(travelHistory);
            updated = true;
        }

        if (reviewDTO.getBundleId() != null &&
            !existingReview.getBundle().getId().equals(reviewDTO.getBundleId())) {
            Bundle bundle = bundleRepository.findById(reviewDTO.getBundleId())
                .orElseThrow(() -> new RuntimeException("Pacote não encontrado"));
            existingReview.setBundle(bundle);
            updated = true;
        }

        if (!updated) {
            throw new IllegalArgumentException("Nenhuma alteração foi detectada na avaliação");
        }

        Review savedReview = reviewRepository.save(existingReview);
        return convertToDTO(savedReview);
    }

    public ReviewDTO updateReviewByUser(Integer id, Integer userId, ReviewDTO reviewDTO) {
        // Busca o review apenas se for do usuário autenticado
        Review existingReview = reviewRepository.findByIdAndUserId(id, userId);
        if (existingReview == null) {
            throw new IllegalArgumentException("Você só pode atualizar suas próprias avaliações.");
        }
        boolean updated = false;
        // Atualiza apenas se o campo vier preenchido no JSON
        if (reviewDTO.getRating() != null && !reviewDTO.getRating().equals(existingReview.getRating())) {
            existingReview.setRating(reviewDTO.getRating());
            updated = true;
        }
        if (reviewDTO.getComment() != null && !reviewDTO.getComment().equals(existingReview.getComment())) {
            existingReview.setComment(reviewDTO.getComment());
            updated = true;
        }
        // Só atualiza travelHistory se vier no JSON e for diferente
        if (reviewDTO.getTravelHistoryId() != null &&
            (existingReview.getTravelHistory() == null || !existingReview.getTravelHistory().getId().equals(reviewDTO.getTravelHistoryId()))) {
            TravelHistory travelHistory = travelHistoryRepository.findById(reviewDTO.getTravelHistoryId())
                .orElseThrow(() -> new RuntimeException("Histórico de viagem não encontrado"));
            existingReview.setTravelHistory(travelHistory);
            updated = true;
        }
        // Só atualiza bundle se vier no JSON e for diferente
        if (reviewDTO.getBundleId() != null &&
            (existingReview.getBundle() == null || !existingReview.getBundle().getId().equals(reviewDTO.getBundleId()))) {
            Bundle bundle = bundleRepository.findById(reviewDTO.getBundleId())
                .orElseThrow(() -> new RuntimeException("Pacote não encontrado"));
            existingReview.setBundle(bundle);
            updated = true;
        }
        if (!updated) {
            throw new IllegalArgumentException("Nenhuma alteração foi detectada na avaliação");
        }
        Review savedReview = reviewRepository.save(existingReview);
        return convertToDTO(savedReview);
    }

    public void deleteReview(Integer id) {
        if (!reviewRepository.existsById(id)) {
            throw new RuntimeException("Avaliação não encontrada");
        }
        reviewRepository.deleteById(id);
    }

    public List<ReviewDTO> getReviewsByRating(Rating rating) {
        if (rating == null) {
            throw new IllegalArgumentException("Rating não pode ser nulo");
        }
        return reviewRepository.findByRating(rating).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ReviewDTO> getReviewsByMinimumRating(Rating minRating) {
        if (minRating == null) {
            throw new IllegalArgumentException("Rating mínimo não pode ser nulo");
        }
        return reviewRepository.findByMinimumRating(minRating).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ReviewDTO> getReviewsWithComment() {
        return reviewRepository.findByCommentIsNotNull().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Double getAverageRating() {
        Double average = reviewRepository.getAverageRating();
        return average != null ? average : 0.0;
    }

    public List<ReviewDTO> getReviewsByUser(Integer userId) {
        return reviewRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ReviewDTO> getReviewsByBundle(Integer bundleId) {
        if (bundleId == null) {
            throw new IllegalArgumentException("ID do bundle não pode ser nulo");
        }
        return reviewRepository.findByBundleId(bundleId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ReviewDTO convertToDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setAvaliationDate(review.getAvaliationDate());
        dto.setTravelHistoryId(review.getTravelHistory() != null ? review.getTravelHistory().getId() : null);
        dto.setBundleId(review.getBundle() != null ? review.getBundle().getId() : null);
        return dto;
    }

    private Review convertToEntity(ReviewDTO dto) {
        Review review = new Review();
        review.setId(dto.getId());
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setAvaliationDate(dto.getAvaliationDate());

        if (dto.getTravelHistoryId() != null) {
            TravelHistory travelHistory = travelHistoryRepository.findById(dto.getTravelHistoryId())
                .orElseThrow(() -> new RuntimeException("Histórico de viagem não encontrado"));
            review.setTravelHistory(travelHistory);
        }

        if (dto.getBundleId() != null) {
            Bundle bundle = bundleRepository.findById(dto.getBundleId())
                .orElseThrow(() -> new RuntimeException("Pacote não encontrado"));
            review.setBundle(bundle);
        }

        return review;
    }
}
