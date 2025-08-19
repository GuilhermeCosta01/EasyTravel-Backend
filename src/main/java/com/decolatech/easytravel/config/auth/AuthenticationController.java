package com.decolatech.easytravel.config.auth;

import com.decolatech.easytravel.common.EmailService;
import com.decolatech.easytravel.common.Resposta;
import com.decolatech.easytravel.config.security.SecurityConfiguration;
import com.decolatech.easytravel.config.security.TokenService;
import com.decolatech.easytravel.domain.user.dto.AuthenticationDTO;
import com.decolatech.easytravel.domain.user.dto.LoginResponseDTO;
import com.decolatech.easytravel.domain.user.dto.RegisterDTO;
import com.decolatech.easytravel.domain.user.enums.UserRole;
import com.decolatech.easytravel.domain.user.dto.UserDTO;
import com.decolatech.easytravel.domain.user.entity.User;
import com.decolatech.easytravel.domain.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
@RestController
@RequestMapping("/auth")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
public class AuthenticationController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    private Resposta resposta;

    @PostMapping("/login")
    public ResponseEntity login (@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));

    }

    @PostMapping("/register/admin")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO data) {
        try {
            if (this.userRepository.findByLoginEmail(data.email()) != null) {
                throw new com.decolatech.easytravel.domain.user.exception.EmailAlreadyExistsException("Erro! Esse e-mail já está cadastrado");
            }
            if (this.userRepository.findByCpf(data.cpf()) != null) {
                throw new com.decolatech.easytravel.domain.user.exception.CpfAlreadyExistsException("Erro! Esse CPF já está cadastrado");
            }
            if (data.passport() != null && this.userRepository.findByPassport(data.passport()) != null) {
                throw new com.decolatech.easytravel.domain.user.exception.PassportAlreadyExistsException("Erro! Esse passaporte já está cadastrado");
            }
            if (data.cpf() == null || data.cpf().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Resposta(400, "Erro! O CPF é obrigatorio!"));
            }
            String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
            User user = new User(data.name(), data.email(), data.cpf(), data.passport(), encryptedPassword, data.telephone(), data.role());
            this.userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).build();
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

    @PostMapping("/register/client")
    public ResponseEntity<?> registerClient(@RequestBody @Valid RegisterDTO data) {
        try {
            if (this.userRepository.findByLoginEmail(data.email()) != null) {
                throw new com.decolatech.easytravel.domain.user.exception.EmailAlreadyExistsException("Erro! Esse e-mail já está cadastrado");
            }
            if (this.userRepository.findByCpf(data.cpf()) != null) {
                throw new com.decolatech.easytravel.domain.user.exception.CpfAlreadyExistsException("Erro! Esse CPF já está cadastrado");
            }
            if (data.passport() != null && this.userRepository.findByPassport(data.passport()) != null) {
                throw new com.decolatech.easytravel.domain.user.exception.PassportAlreadyExistsException("Erro! Esse passaporte já está cadastrado");
            }
            if (data.cpf() == null || data.cpf().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Resposta(400, "Erro! O CPF é obrigatorio!"));
            }
            String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
            // Força o papel USER, independente do que vier no DTO
            User user = new User(
                    data.name(),
                    data.email(),
                    data.cpf(),
                    data.passport(),
                    encryptedPassword,
                    data.telephone(),
                    UserRole.USER // força o papel USER
            );
            this.userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).build();
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

    @GetMapping("/loadForgotPassword")
    public String loadForgotPassword() {
        return "forgot-password";
    }

    @GetMapping("/loadResetPassword")
    public String loadResetPassword() {
        return "reset-password";
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null || email.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Resposta(400, "O campo 'email' é obrigatório."));
        }
        User user = userRepository.findByEmail(email);
        if (user != null) {
            // Gera um token JWT temporário para recuperação de senha
            String token = tokenService.generateRecoveryToken(user);
            String resetLink = "https://seusite.com/reset-password?token=" + token;
            // Envia o e-mail de recuperação de senha
            String subject = "Recuperação de senha - EasyTravel";
            String text = "Olá, " + user.getName() + "!\n\n" +
                    "Recebemos uma solicitação para redefinir sua senha.\n" +
                    "Clique no link abaixo para criar uma nova senha:\n" +
                    resetLink + "\n\n" +
                    "Se você não solicitou a redefinição, ignore este e-mail.";
            emailService.sendEmail(user.getEmail(), subject, text);
            return ResponseEntity.ok(new Resposta(200, "E-mail de recuperação enviado com sucesso."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Resposta(404, "Usuário não encontrado com o e-mail informado."));
        }
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestParam("token") String token, @RequestParam("newPassword") String newPassword) {
        String email = tokenService.validateRecoveryToken(token);
        if (email == null || email.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Resposta(400, "Token inválido ou expirado."));
        }
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Resposta(404, "Usuário não encontrado."));
        }
        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        userRepository.save(user);
        // Opcional: notificar o usuário por e-mail que a senha foi alterada
        String subject = "Senha redefinida com sucesso - EasyTravel";
        String text = "Olá, " + user.getName() + "!\n\nSua senha foi redefinida com sucesso.";
        emailService.sendEmail(user.getEmail(), subject, text);
        return ResponseEntity.ok(new Resposta(200, "Senha redefinida com sucesso."));
    }
}