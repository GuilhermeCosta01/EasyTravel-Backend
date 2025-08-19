package com.decolatech.easytravel.domain.booking.controller;

import com.decolatech.easytravel.config.security.SecurityConfiguration;
import com.decolatech.easytravel.common.Resposta;
import com.decolatech.easytravel.domain.booking.dto.ReviewDTO;
import com.decolatech.easytravel.domain.booking.service.ReviewService;
import com.decolatech.easytravel.domain.booking.enums.Rating;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@Tag(name = "Reviews", description = "API para gerenciamento de avaliações")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Operation(summary = "Listar todas as avaliações")
    @GetMapping
    public ResponseEntity<?> getAllReviews() {
        try {
            List<ReviewDTO> reviews = reviewService.getAllReviews();
            return ResponseEntity.status(HttpStatus.OK).body(reviews);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor: " + e.getMessage()));
        }
    }

    @Operation(summary = "Buscar avaliação por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getReviewById(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                .body(reviewService.getReviewById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Resposta(404, "Avaliação não encontrada"));
        }
    }

    @Operation(summary = "Criar nova avaliação")
    @PostMapping
    public ResponseEntity<?> createReview(@Valid @RequestBody ReviewDTO reviewDTO) {
        try {
            ReviewDTO createdReview = reviewService.createReview(reviewDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
        } catch (com.decolatech.easytravel.domain.booking.exception.OneReviewPerTravelHistoryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new Resposta(409, e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, "Erro ao criar avaliação: " + e.getMessage()));
        }
    }



    @Operation(summary = "Deletar avaliação")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Integer id) {
        try {
            reviewService.deleteReview(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new Resposta(204, "Avaliação deletada com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Resposta(404, "Avaliação não encontrada"));
        }
    }

    @Operation(summary = "Buscar avaliações por rating")
    @GetMapping("/rating/{rating}")
    public ResponseEntity<?> getReviewsByRating(@PathVariable Rating rating) {
        try {
            List<ReviewDTO> reviews = reviewService.getReviewsByRating(rating);
            return ResponseEntity.status(HttpStatus.OK).body(reviews);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor ao buscar avaliações por rating: " + e.getMessage()));
        }
    }

    @Operation(summary = "Buscar avaliações por ID do pacote")
    @GetMapping("/bundle/{bundleId}")
    public ResponseEntity<?> getReviewsByBundle(@PathVariable Integer bundleId) {
        try {
            List<ReviewDTO> reviews = reviewService.getReviewsByBundle(bundleId);
            return ResponseEntity.status(HttpStatus.OK).body(reviews);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor ao buscar avaliações por pacote: " + e.getMessage()));
        }
    }

    @Operation(summary = "Buscar avaliações com rating mínimo")
    @GetMapping("/minimum-rating/{minRating}")
    public ResponseEntity<?> getReviewsByMinimumRating(@PathVariable Rating minRating) {
        try {
            List<ReviewDTO> reviews = reviewService.getReviewsByMinimumRating(minRating);
            return ResponseEntity.status(HttpStatus.OK).body(reviews);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor ao buscar avaliações por rating mínimo: " + e.getMessage()));
        }
    }

    @Operation(summary = "Buscar avaliações com comentário")
    @GetMapping("/with-comment")
    public ResponseEntity<?> getReviewsWithComment() {
        try {
            List<ReviewDTO> reviews = reviewService.getReviewsWithComment();
            return ResponseEntity.status(HttpStatus.OK).body(reviews);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor ao buscar avaliações com comentário: " + e.getMessage()));
        }
    }

    @Operation(summary = "Obter média de avaliações")
    @GetMapping("/average")
    public ResponseEntity<?> getAverageRating() {
        try {
            Double average = reviewService.getAverageRating();
            return ResponseEntity.status(HttpStatus.OK).body(average);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor ao calcular média das avaliações: " + e.getMessage()));
        }
    }

    @Operation(summary = "Listar avaliações do usuário autenticado")
    @GetMapping("/my")
    public ResponseEntity<?> getMyReviews(org.springframework.security.core.Authentication authentication) {
        com.decolatech.easytravel.domain.user.entity.User user = (com.decolatech.easytravel.domain.user.entity.User) authentication.getPrincipal();
        java.util.List<ReviewDTO> reviews = reviewService.getReviewsByUser(user.getId());
        return ResponseEntity.ok(reviews);
    }

    @Operation(summary = "Atualizar avaliação do usuário autenticado")
    @PutMapping("/my/{id}")
    public ResponseEntity<?> updateMyReview(@PathVariable Integer id, @Valid @RequestBody ReviewDTO reviewDTO, org.springframework.security.core.Authentication authentication) {
        try {
            com.decolatech.easytravel.domain.user.entity.User user = (com.decolatech.easytravel.domain.user.entity.User) authentication.getPrincipal();
            // Chama o service que garante que só o dono pode atualizar
            ReviewDTO updatedReview = reviewService.updateReviewByUser(id, user.getId(), reviewDTO);
            return ResponseEntity.ok(updatedReview);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new com.decolatech.easytravel.common.Resposta(400, e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new com.decolatech.easytravel.common.Resposta(404, "Avaliação não encontrada"));
        }
    }
}
