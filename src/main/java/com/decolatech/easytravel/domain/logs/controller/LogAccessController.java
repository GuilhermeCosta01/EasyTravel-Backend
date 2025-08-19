package com.decolatech.easytravel.domain.logs.controller;

import com.decolatech.easytravel.config.security.SecurityConfiguration;
import com.decolatech.easytravel.common.Resposta;
import com.decolatech.easytravel.domain.logs.dto.LogAccessDTO;
import com.decolatech.easytravel.domain.logs.service.LogAccessService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/logs/access")
@Tag(name = "Log Access", description = "API para gerenciamento de logs de acesso")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
public class LogAccessController {

    @Autowired
    private LogAccessService logAccessService;

    @Operation(summary = "Listar todos os logs de acesso")
    @GetMapping
    public ResponseEntity<?> getAllLogAccess() {
        try {
            List<LogAccessDTO> logAccess = logAccessService.getAllLogAccess();
            return ResponseEntity.status(HttpStatus.OK).body(logAccess);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor: " + e.getMessage()));
        }
    }

    @Operation(summary = "Buscar log de acesso por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getLogAccessById(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                .body(logAccessService.getLogAccessById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Resposta(404, "Log de acesso não encontrado"));
        }
    }

    @Operation(summary = "Criar novo log de acesso")
    @PostMapping
    public ResponseEntity<?> createLogAccess(@Valid @RequestBody LogAccessDTO logAccessDTO) {
        try {
            LogAccessDTO createdLogAccess = logAccessService.createLogAccess(logAccessDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLogAccess);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, "Erro ao criar log de acesso: " + e.getMessage()));
        }
    }

    @Operation(summary = "Deletar log de acesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLogAccess(@PathVariable Integer id) {
        try {
            logAccessService.deleteLogAccess(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new Resposta(204, "Log de acesso deletado com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Resposta(404, "Log de acesso não encontrado"));
        }
    }

    @Operation(summary = "Buscar logs por usuário")
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getLogAccessByUser(@PathVariable Integer userId) {
        try {
            List<LogAccessDTO> logAccess = logAccessService.getLogAccessByUser(userId);
            return ResponseEntity.status(HttpStatus.OK).body(logAccess);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor ao buscar logs por usuário: " + e.getMessage()));
        }
    }

    @Operation(summary = "Buscar logs por tipo de ação")
    @GetMapping("/action-type/{actionType}")
    public ResponseEntity<?> getLogAccessByActionType(@PathVariable String actionType) {
        try {
            List<LogAccessDTO> logAccess = logAccessService.getLogAccessByActionType(actionType);
            return ResponseEntity.status(HttpStatus.OK).body(logAccess);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor ao buscar logs por tipo de ação: " + e.getMessage()));
        }
    }

    @Operation(summary = "Buscar logs por período")
    @GetMapping("/date-range")
    public ResponseEntity<?> getLogAccessByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        try {
            List<LogAccessDTO> logAccess = logAccessService.getLogAccessByDateRange(startDate, endDate);
            return ResponseEntity.status(HttpStatus.OK).body(logAccess);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor ao buscar logs por período: " + e.getMessage()));
        }
    }
}
