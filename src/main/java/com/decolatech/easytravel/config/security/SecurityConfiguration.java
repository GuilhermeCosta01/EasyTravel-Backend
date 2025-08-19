package com.decolatech.easytravel.config.security;

import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@SecurityScheme(name = SecurityConfiguration.SECURITY, type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class SecurityConfiguration {

    public static final String SECURITY = "Bearer token";

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_ADMIN > ROLE_EMPLOYEE \n ROLE_EMPLOYEE > ROLE_USER";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
                    // Para Azure App Service, usar apenas origens específicas sem patterns quando allowCredentials=true
                    corsConfiguration.setAllowedOrigins(java.util.List.of(
                        "https://back-et.azurewebsites.net",
                        "http://localhost:4200",
                        "http://localhost:3000",
                        "http://localhost:8080",
                        "https://localhost:8080"
                    ));
                    corsConfiguration.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
                    corsConfiguration.setAllowedHeaders(java.util.List.of("*"));
                    corsConfiguration.setAllowCredentials(true);
                    corsConfiguration.setExposedHeaders(java.util.List.of("Authorization", "Content-Type"));
                    corsConfiguration.setMaxAge(3600L);
                    return corsConfiguration;
                }))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/swagger-ui/index.html", "/api-docs", "/api-docs/**", "/swagger-resources/**", "/webjars/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/auth/register/client").permitAll()
                        .requestMatchers("/auth/register/admin").permitAll()
                        .requestMatchers("/auth/forgotPassword").permitAll()
                        .requestMatchers("/auth/resetPassword").permitAll()
                        .requestMatchers("/api/bundle-locations/route").permitAll()
                        .requestMatchers("/").permitAll() // Permitir acesso público à rota raiz (/)
                        .requestMatchers("/test-images.html").permitAll() // Permitir acesso à página de teste de imagens
                        .requestMatchers(GET, "/api/bundles").permitAll()
                        .requestMatchers(PUT, "/api/bundles/{id}").permitAll()
                        .requestMatchers(DELETE, "/api/bundles/{id}").permitAll()
                        .requestMatchers(POST, "/api/bundles").permitAll()




                        // Permitir acesso público às rotas que precisamos para o funcionamento do sistema
                        .requestMatchers("/api/medias/**").permitAll()
                        .requestMatchers("/uploads/**").permitAll() // Permitir acesso público às uploads
                        .requestMatchers(GET, "/api/bundles/available").permitAll() // Permitir acesso geral aos pacotes disponíveis
                        .requestMatchers(GET, "/api/bundle-locations/*").permitAll() // Permitir acesso geral para buscar localização de pacote por ID
                        .requestMatchers(GET, "/api/reviews/bundle/*").permitAll() // Permitir acesso público às avaliações por bundle ID
                        .requestMatchers(GET, "/api/reservations/my").permitAll() // Permitir confirmação de reserva própria
                        .requestMatchers(GET, "/api/bundle-locations/bundle/{bundleId}").permitAll()
                        .requestMatchers(GET, "/api/bundles/user/{userId}").permitAll() // Permitir acesso público ao endpoint GET /api/bundles/user/{userId}
                        .requestMatchers(POST, "/api/bundle-locations").permitAll()
                        .requestMatchers(GET, "/api/reviews").permitAll()
                        .requestMatchers(GET, "/api/travel-histories").permitAll()
                        .requestMatchers(GET, "/api/reservations").permitAll()
                        .requestMatchers(GET, "/api/users").permitAll()
                        .requestMatchers(GET, "/api/reviews").permitAll()
                        // Na nossa logica nos não deletamos a reserva que o usuario queira
                        //  cancelar apos o pagamento apenas cancelamos mudando o status(obrigação do emplyee/admin)
                        .requestMatchers(GET,"/api/reservations/{id}").permitAll()
                        .requestMatchers(GET,"/api/bundles/{id}").permitAll()
                        .requestMatchers(GET, "/api/locations").permitAll() // Permitir acesso público ao endpoint GET /api/locations
                        .requestMatchers(GET, "/api/locations/search").permitAll() // Permitir acesso público ao endpoint GET /api/locations/search
                        .requestMatchers(POST, "/api/reviews").permitAll()
                        // Rotas específicas para USER
                        .requestMatchers(POST, "/api/reservations").hasRole("USER")
                        .requestMatchers(GET, "/api/reservations/my").hasRole("USER")
                        .requestMatchers("/api/reservations/**").hasRole("USER")
                        .requestMatchers(POST, "/api/payments").hasRole("USER")
                        .requestMatchers(GET, "/api/payments/*/export-to-pdf").hasRole("USER")
                        .requestMatchers(POST, "/api/travel-histories").hasRole("USER")
                        .requestMatchers(GET, "/api/travel-histories/my").hasRole("USER")
                        .requestMatchers(GET, "/api/travel-histories").hasRole("USER")


                        .requestMatchers(PUT, "/api/reviews/my/{id}").hasRole("USER")
                        .requestMatchers(PATCH, "/api/reservations/{id}/confirm/my").hasRole("USER")
                        .requestMatchers(PATCH, "/api/reservations/{id}/cancel/my").hasRole("USER")
                        .requestMatchers(POST, "/api/travelers").hasRole("USER")
                        .requestMatchers(GET, "/api/travelers/reservation/{reservationId}/my").hasRole("USER")
                        .requestMatchers(PUT, "/api/travelers/{id}").hasRole("USER")

                        // Rotas específicas de bundles para USER
                        .requestMatchers(GET, "/api/bundles").hasRole("USER") // Listar todos os pacotes
                        .requestMatchers(GET, "/api/bundles/rank/**").hasRole("USER") // Buscar pacotes por rank
                        .requestMatchers(GET, "/api/bundles/available").hasRole("USER") // Buscar pacotes disponíveis
                        .requestMatchers(GET, "/api/bundles/search").hasRole("USER") // Buscar pacotes por título
                        .requestMatchers(GET, "/api/bundles/price-range").hasRole("USER") // Buscar pacotes por faixa de preço

                        // Rotas específicas para Employee e admin
                        .requestMatchers("/api/users").permitAll()
                        .requestMatchers("/api/travelers/**").permitAll()
                        .requestMatchers("/api/reviews/**").hasRole("USER")

                        .requestMatchers("/api/payments/**").permitAll()
                        .requestMatchers("/api/travel-histories/**").hasRole("USER")
                        .requestMatchers("/api/users/**").hasRole("USER") // Permitir acesso a usuários autenticados
                        .requestMatchers("/api/bundles/**").hasRole("USER") // Outras rotas de bundles (CRUD operations)
                        .requestMatchers("/api/locations/**").hasRole("EMPLOYEE")


                        //ROTAS ESPECIFICAS DO ADMIN

                        .requestMatchers("/api/dashboard/**").hasRole("ADMIN")
                        .requestMatchers("/api/dashboard/export/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
