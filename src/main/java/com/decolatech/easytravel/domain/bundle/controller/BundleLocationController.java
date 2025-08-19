package com.decolatech.easytravel.domain.bundle.controller;

import com.decolatech.easytravel.config.security.SecurityConfiguration;
import com.decolatech.easytravel.common.Resposta;
import com.decolatech.easytravel.domain.bundle.dto.BundleLocationDTO;
import com.decolatech.easytravel.domain.bundle.service.BundleLocationService;
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
@RequestMapping("/api/bundle-locations")
@Tag(name = "Bundle Locations", description = "API para gerenciamento de localizações de pacotes")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
public class BundleLocationController {

    @Autowired
    private BundleLocationService bundleLocationService;

    @Operation(summary = "Listar todas as localizações de pacotes")
    @GetMapping
    public ResponseEntity<?> getAllBundleLocations() {
        try {
            List<BundleLocationDTO> bundleLocations = bundleLocationService.getAllBundleLocations();
            return ResponseEntity.status(HttpStatus.OK).body(bundleLocations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor: " + e.getMessage()));
        }
    }

    @Operation(summary = "Buscar localização de pacote por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getBundleLocationById(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                .body(bundleLocationService.getBundleLocationById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Resposta(404, "Localização de pacote não encontrada"));
        }
    }

    @Operation(summary = "Criar nova localização de pacote")
    @PostMapping
    public ResponseEntity<?> createBundleLocation(@Valid @RequestBody BundleLocationDTO bundleLocationDTO) {
        try {
            BundleLocationDTO createdBundleLocation = bundleLocationService.createBundleLocation(bundleLocationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBundleLocation);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, "Erro ao criar localização de pacote: " + e.getMessage()));
        }
    }

    @Operation(summary = "Atualizar localização de pacote")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBundleLocation(@PathVariable Integer id, @Valid @RequestBody BundleLocationDTO bundleLocationDTO) {
        try {
            BundleLocationDTO updatedBundleLocation = bundleLocationService.updateBundleLocation(id, bundleLocationDTO);
            return ResponseEntity.status(HttpStatus.OK).body(updatedBundleLocation);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Resposta(404, "Localização de pacote não encontrada"));
        }
    }

    @Operation(summary = "Deletar localização de pacote")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBundleLocation(@PathVariable Integer id) {
        try {
            bundleLocationService.deleteBundleLocation(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new Resposta(204, "Localização de pacote deletada com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Resposta(404, "Localização de pacote não encontrada"));
        }
    }

    @Operation(summary = "Buscar localizações por bundle")
    @GetMapping("/bundle/{bundleId}")
    public ResponseEntity<?> getBundleLocationsByBundle(@PathVariable Integer bundleId) {
        try {
            List<BundleLocationDTO> bundleLocations = bundleLocationService.getBundleLocationsByBundle(bundleId);
            return ResponseEntity.status(HttpStatus.OK).body(bundleLocations);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor ao buscar localizações por bundle: " + e.getMessage()));
        }
    }

    @Operation(summary = "Buscar pacotes por rota")
    @GetMapping("/route")
    public ResponseEntity<?> getBundleLocationsByRoute(
            @RequestParam Integer departureId,
            @RequestParam Integer destinationId) {
        try {
            List<BundleLocationDTO> bundleLocations = bundleLocationService.getBundleLocationsByRoute(departureId, destinationId);
            return ResponseEntity.status(HttpStatus.OK).body(bundleLocations);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor ao buscar pacotes por rota: " + e.getMessage()));
        }
    }
}
