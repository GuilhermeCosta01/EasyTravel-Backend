package com.decolatech.easytravel.domain.bundle.controller;

import com.decolatech.easytravel.config.security.SecurityConfiguration;
import com.decolatech.easytravel.common.Resposta;
import com.decolatech.easytravel.domain.bundle.dto.LocationDTO;
import com.decolatech.easytravel.domain.bundle.service.LocationService;
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
@RequestMapping("/api/locations")
@Tag(name = "Locations", description = "API para gerenciamento de localizações")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Operation(summary = "Listar todas as localizações")
    @GetMapping
    public ResponseEntity<?> getAllLocations() {
        try {
            List<LocationDTO> locations = locationService.getAllLocations();
            return ResponseEntity.status(HttpStatus.OK).body(locations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor: " + e.getMessage()));
        }
    }

    @Operation(summary = "Buscar localização por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getLocationById(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                .body(locationService.getLocationById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Resposta(404, "Localização não encontrada"));
        }
    }

    @Operation(summary = "Criar nova localização")
    @PostMapping
    public ResponseEntity<?> createLocation(@Valid @RequestBody LocationDTO locationDTO) {
        try {
            LocationDTO createdLocation = locationService.createLocation(locationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLocation);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, "Erro ao criar localização: " + e.getMessage()));
        }
    }

    @Operation(summary = "Atualizar localização")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLocation(@PathVariable Integer id, @Valid @RequestBody LocationDTO locationDTO) {
        try {
            LocationDTO updatedLocation = locationService.updateLocation(id, locationDTO);
            return ResponseEntity.status(HttpStatus.OK).body(updatedLocation);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Resposta(404, "Localização não encontrada"));
        }
    }

    @Operation(summary = "Deletar localização")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLocation(@PathVariable Integer id) {
        try {
            locationService.deleteLocation(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new Resposta(204, "Localização deletada com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Resposta(404, "Localização não encontrada"));
        }
    }

    @Operation(summary = "Buscar localizações por país")
    @GetMapping("/country/{country}")
    public ResponseEntity<?> getLocationsByCountry(@PathVariable String country) {
        try {
            List<LocationDTO> locations = locationService.getLocationsByCountry(country);
            return ResponseEntity.status(HttpStatus.OK).body(locations);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor ao buscar localizações por país: " + e.getMessage()));
        }
    }

    @Operation(summary = "Buscar localizações por cidade")
    @GetMapping("/search")
    public ResponseEntity<?> searchLocationsByCity(@RequestParam String city) {
        try {
            List<LocationDTO> locations = locationService.searchLocationsByCity(city);
            return ResponseEntity.status(HttpStatus.OK).body(locations);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor ao buscar localizações por cidade: " + e.getMessage()));
        }
    }
}
