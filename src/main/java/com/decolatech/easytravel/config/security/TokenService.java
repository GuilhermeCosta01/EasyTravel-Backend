package com.decolatech.easytravel.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.decolatech.easytravel.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String role = user.getUserRole() != null ? user.getUserRole().toString() : "USER"; // valor padrão se nulo
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getEmail())
                    .withClaim("role", role)
                    .withClaim("name",user.getName())
                    .withClaim("id", user.getId())
                    .withClaim("passport",user.getPassport())
                    .withClaim("telefone", user.getTelephone())
                    .withExpiresAt(LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.of("-03:00"))) // Expira em 2 horas
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro na geração do token: " + e.getMessage(), e);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return "";
        }
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String generateRecoveryToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // Token de recuperação expira em 30 minutos
            String token = JWT.create()
                    .withIssuer("recovery-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(LocalDateTime.now().plusMinutes(30).toInstant(ZoneOffset.of("-03:00")))
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro na geração do token de recuperação: " + e.getMessage(), e);
        }
    }

    public String validateRecoveryToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("recovery-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    // Decodifica o token e retorna o objeto DecodedJWT para acessar claims
    public com.auth0.jwt.interfaces.DecodedJWT decodeToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm)
                .withIssuer("auth-api")
                .build()
                .verify(token);
    }
}
