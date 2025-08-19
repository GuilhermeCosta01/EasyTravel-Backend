package com.decolatech.easytravel.domain.booking.controller;

import com.decolatech.easytravel.config.security.SecurityConfiguration;
import com.decolatech.easytravel.common.Resposta;
import com.decolatech.easytravel.domain.booking.dto.TravelHistoryDTO;
import com.decolatech.easytravel.domain.booking.service.TravelHistoryService;
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
@RequestMapping("/api/travel-histories")
@Tag(name = "Travel Histories", description = "API para gerenciamento de histórico de viagens")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
public class TravelHistoryController {

    @Autowired
    private TravelHistoryService travelHistoryService;

    @Operation(summary = "Listar todos os históricos de viagem")
    @GetMapping
    public ResponseEntity<?> getAllTravelHistories() {
        try {
            List<TravelHistoryDTO> travelHistories = travelHistoryService.getAllTravelHistories();
            return ResponseEntity.status(HttpStatus.OK).body(travelHistories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor: " + e.getMessage()));
        }
    }

    @Operation(summary = "Buscar histórico de viagem por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getTravelHistoryById(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                .body(travelHistoryService.getTravelHistoryById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Resposta(404, "Histórico de viagem não encontrado"));
        }
    }

    @Operation(summary = "Criar novo histórico de viagem")
    @PostMapping
    public ResponseEntity<?> createTravelHistory(@Valid @RequestBody TravelHistoryDTO travelHistoryDTO) {
        try {
            TravelHistoryDTO createdTravelHistory = travelHistoryService.createTravelHistory(travelHistoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTravelHistory);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, "Erro ao criar histórico de viagem: " + e.getMessage()));
        }
    }

//    @Operation(summary = "Atualizar histórico de viagem")
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateTravelHistory(@PathVariable Integer id, @Valid @RequestBody TravelHistoryDTO travelHistoryDTO) {
//        try {
//            TravelHistoryDTO updatedTravelHistory = travelHistoryService.updateTravelHistory(id, travelHistoryDTO);
//            return ResponseEntity.status(HttpStatus.OK).body(updatedTravelHistory);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body(new Resposta(400, e.getMessage()));
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body(new Resposta(404, "Histórico de viagem não encontrado"));
//        }
//    }

    @Operation(summary = "Deletar histórico de viagem")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTravelHistory(@PathVariable Integer id) {
        try {
            travelHistoryService.deleteTravelHistory(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new Resposta(204, "Histórico de viagem deletado com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Resposta(404, "Histórico de viagem não encontrado"));
        }
    }


    @Operation(summary = "Buscar históricos por pagamento")
    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<?> getTravelHistoriesByPayment(@PathVariable Integer paymentId) {
        try {
            List<TravelHistoryDTO> travelHistories = travelHistoryService.getTravelHistoriesByPayment(paymentId);
            return ResponseEntity.status(HttpStatus.OK).body(travelHistories);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor ao buscar históricos por pagamento: " + e.getMessage()));
        }
    }

    @Operation(summary = "Listar históricos de viagem do usuário autenticado")
    @GetMapping("/my")
    public ResponseEntity<?> getMyTravelHistories(org.springframework.security.core.Authentication authentication) {
        com.decolatech.easytravel.domain.user.entity.User user = (com.decolatech.easytravel.domain.user.entity.User) authentication.getPrincipal();
        List<TravelHistoryDTO> travelHistories = travelHistoryService.getTravelHistoriesByUser(user.getId());
        return ResponseEntity.ok(travelHistories);
    }
}
