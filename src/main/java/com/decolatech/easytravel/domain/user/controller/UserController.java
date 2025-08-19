package com.decolatech.easytravel.domain.user.controller;

import com.decolatech.easytravel.common.Resposta;
import com.decolatech.easytravel.config.security.SecurityConfiguration;
import com.decolatech.easytravel.domain.user.dto.UserDTO;
import com.decolatech.easytravel.domain.user.enums.UserRole;
import com.decolatech.easytravel.domain.user.enums.UserStatus;
import com.decolatech.easytravel.domain.user.service.UserService;
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
@RequestMapping("/api/users")
@Tag(name = "Users", description = "API para gerenciamento de usuários")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Listar todos os usuários")
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            List<UserDTO> users = userService.getAllUsers();
            return ResponseEntity.status(HttpStatus.OK).body(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor" + e.getMessage()));
        }
    }

    @Operation(summary = "Buscar usuário por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getUserById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Resposta(404,"Usuário não encontrado"));
        }
    }

    @Operation(summary = "Criar novo usuário")
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO) {
        try {
            UserDTO createdUser = userService.createUser(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (com.decolatech.easytravel.domain.user.exception.EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new Resposta(409, e.getMessage()));
        } catch (com.decolatech.easytravel.domain.user.exception.CpfAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new Resposta(409, e.getMessage()));
        } catch (com.decolatech.easytravel.domain.user.exception.PassportAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new Resposta(409, e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, "Erro ao criar usuário: " + e.getMessage()));
        }
    }

    @Operation(summary = "Atualizar usuário")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @Valid @RequestBody UserDTO userDTO) {
        try {
            // Criptografa a senha se ela estiver presente no DTO
            if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
                String encryptedPassword = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode(userDTO.getPassword());
                userDTO.setPassword(encryptedPassword);
            }
            UserDTO updatedUser = userService.updateUser(id, userDTO);
            return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Resposta(404, e.getMessage()));
        }
    }

    @Operation(summary = "Deletar usuário")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new Resposta(204, "Usuário deletado com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Resposta(404, "Usuário não encontrado"));
        }
    }

    @Operation(summary = "Buscar usuários por role")
    @GetMapping("/role/{roles}")
    public ResponseEntity<?> getUsersByRole(@PathVariable UserRole roles) {
        try {
            List<UserDTO> users = userService.getUsersByRole(roles);
            return ResponseEntity.status(HttpStatus.OK).body(users);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Resposta(404, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor ao buscar usuários por role" + e.getMessage()));
        }
    }

    @Operation(summary = "Buscar usuários por status")
    @GetMapping("/status/{status}")
    public ResponseEntity<?> getUsersByStatus(@PathVariable UserStatus status) {
        try {
            List<UserDTO> users = userService.getUsersByStatus(status);
            return ResponseEntity.status(HttpStatus.OK).body(users);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Resposta(404, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Resposta(500, "Erro interno do servidor ao buscar usuários por status" + e.getMessage()));
        }
    }

}
