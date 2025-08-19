package com.decolatech.easytravel.domain.booking.controller;

import com.decolatech.easytravel.config.security.SecurityConfiguration;
import com.decolatech.easytravel.common.Resposta;
import com.decolatech.easytravel.domain.booking.dto.TravelerDTO;
import com.decolatech.easytravel.domain.booking.service.TravelerService;
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
@RequestMapping("/api/travelers")
@Tag(name = "Travelers", description = "API para gerenciamento de viajantes")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
public class TravelerController {

    @Autowired
    private TravelerService travelerService;

    @Operation(summary = "Listar todos os viajantes")
    @GetMapping
    public ResponseEntity<?> getAllTravelers() {
        try {
            List<TravelerDTO> travelers = travelerService.getAllTravelers();
            return ResponseEntity.status(HttpStatus.OK).body(travelers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor: " + e.getMessage()));
        }
    }

    @Operation(summary = "Buscar viajante por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getTravelerById(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                .body(travelerService.getTravelerById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Resposta(404, "Viajante não encontrado"));
        }
    }

    @Operation(summary = "Criar novo viajante")
    @PostMapping
    public ResponseEntity<?> createTraveler(@Valid @RequestBody TravelerDTO travelerDTO) {
        try {
            TravelerDTO createdTraveler = travelerService.createTraveler(travelerDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTraveler);
        } catch (RuntimeException e) {
            String msg = e.getMessage();
            if (msg != null && msg.contains("Já existe um viajante com este tipo e número de documento nesta reserva")) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new com.decolatech.easytravel.common.Resposta(409, msg));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new com.decolatech.easytravel.common.Resposta(400, msg));
        }
    }

    @Operation(summary = "Atualizar viajante (apenas se for da reserva do usuário autenticado)")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTraveler(@PathVariable Integer id, @Valid @RequestBody TravelerDTO travelerDTO) {
        try {
            String username = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
            TravelerDTO updatedTraveler = travelerService.updateTraveler(id, travelerDTO, username);
            return ResponseEntity.status(HttpStatus.OK).body(updatedTraveler);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, e.getMessage()));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new Resposta(403, e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Resposta(404, e.getMessage()));
        }
    }

    @Operation(summary = "Deletar viajante")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTraveler(@PathVariable Integer id) {
        try {
            travelerService.deleteTraveler(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new Resposta(204, "Viajante deletado com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Resposta(404, "Viajante não encontrado"));
        }
    }

    @Operation(summary = "Buscar viajantes por reserva")
    @GetMapping("/reservation/{reservationId}")
    public ResponseEntity<?> getTravelersByReservation(@PathVariable Integer reservationId) {
        try {
            List<TravelerDTO> travelers = travelerService.getTravelersByReservation(reservationId);
            return ResponseEntity.status(HttpStatus.OK).body(travelers);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor ao buscar viajantes por reserva: " + e.getMessage()));
        }
    }

    @Operation(summary = "Buscar viajantes por nome")
    @GetMapping("/search")
    public ResponseEntity<?> searchTravelersByName(@RequestParam String name) {
        try {
            List<TravelerDTO> travelers = travelerService.searchTravelersByName(name);
            return ResponseEntity.status(HttpStatus.OK).body(travelers);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor ao buscar viajantes por nome: " + e.getMessage()));
        }
    }

    @Operation(summary = "Buscar todos os travelers de uma reserva do usuário autenticado")
    @GetMapping("/reservation/{reservationId}/my")
    public ResponseEntity<?> getTravelersByReservationForAuthenticatedUser(@PathVariable Integer reservationId) {
        try {
            String username = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
            List<TravelerDTO> travelers = travelerService.getTravelersByReservationAndUser(reservationId, username);
            return ResponseEntity.status(HttpStatus.OK).body(travelers);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, e.getMessage()));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Resposta(403, e.getMessage()));
        }
    }
}
