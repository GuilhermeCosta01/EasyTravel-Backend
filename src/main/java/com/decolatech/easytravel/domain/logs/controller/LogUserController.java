package com.decolatech.easytravel.domain.logs.controller;

import com.decolatech.easytravel.config.security.SecurityConfiguration;
import com.decolatech.easytravel.common.Resposta;
import com.decolatech.easytravel.domain.logs.dto.LogUserDTO;
import com.decolatech.easytravel.domain.logs.service.LogUserService;
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
@RequestMapping("/api/logs/users")
@Tag(name = "Log Users", description = "API para gerenciamento de logs de usuários")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
public class LogUserController {

    @Autowired
    private LogUserService logUserService;

    @Operation(summary = "Listar todos os logs de usuários")
    @GetMapping
    public ResponseEntity<?> getAllLogUsers() {
        try {
            List<LogUserDTO> logUsers = logUserService.getAllLogUsers();
            return ResponseEntity.status(HttpStatus.OK).body(logUsers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor: " + e.getMessage()));
        }
    }

    @Operation(summary = "Buscar log de usuário por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getLogUserById(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                .body(logUserService.getLogUserById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Resposta(404, "Log de usuário não encontrado"));
        }
    }

    @Operation(summary = "Criar novo log de usuário")
    @PostMapping
    public ResponseEntity<?> createLogUser(@Valid @RequestBody LogUserDTO logUserDTO) {
        try {
            LogUserDTO createdLogUser = logUserService.createLogUser(logUserDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLogUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, "Erro ao criar log de usuário: " + e.getMessage()));
        }
    }

    @Operation(summary = "Deletar log de usuário")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLogUser(@PathVariable Integer id) {
        try {
            logUserService.deleteLogUser(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new Resposta(204, "Log de usuário deletado com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Resposta(404, "Log de usuário não encontrado"));
        }
    }

    @Operation(summary = "Buscar logs por usuário alvo")
    @GetMapping("/target/{userTargetId}")
    public ResponseEntity<?> getLogUsersByTarget(@PathVariable Integer userTargetId) {
        try {
            List<LogUserDTO> logUsers = logUserService.getLogUsersByTarget(userTargetId);
            return ResponseEntity.status(HttpStatus.OK).body(logUsers);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor ao buscar logs por usuário alvo: " + e.getMessage()));
        }
    }

    @Operation(summary = "Buscar logs por usuário que fez a ação")
    @GetMapping("/action/{userActionId}")
    public ResponseEntity<?> getLogUsersByAction(@PathVariable Integer userActionId) {
        try {
            List<LogUserDTO> logUsers = logUserService.getLogUsersByAction(userActionId);
            return ResponseEntity.status(HttpStatus.OK).body(logUsers);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor ao buscar logs por usuário de ação: " + e.getMessage()));
        }
    }

    @Operation(summary = "Buscar logs por campo alterado")
    @GetMapping("/field/{fieldChanged}")
    public ResponseEntity<?> getLogUsersByField(@PathVariable String fieldChanged) {
        try {
            List<LogUserDTO> logUsers = logUserService.getLogUsersByField(fieldChanged);
            return ResponseEntity.status(HttpStatus.OK).body(logUsers);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor ao buscar logs por campo: " + e.getMessage()));
        }
    }

    @Operation(summary = "Buscar logs por período")
    @GetMapping("/date-range")
    public ResponseEntity<?> getLogUsersByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        try {
            List<LogUserDTO> logUsers = logUserService.getLogUsersByDateRange(startDate, endDate);
            return ResponseEntity.status(HttpStatus.OK).body(logUsers);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor ao buscar logs por período: " + e.getMessage()));
        }
    }
}
