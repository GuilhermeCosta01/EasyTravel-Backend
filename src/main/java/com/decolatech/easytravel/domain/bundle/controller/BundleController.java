package com.decolatech.easytravel.domain.bundle.controller;

import com.decolatech.easytravel.config.security.SecurityConfiguration;
import com.decolatech.easytravel.common.Resposta;
import com.decolatech.easytravel.domain.bundle.dto.BundleDTO;
import com.decolatech.easytravel.domain.bundle.enums.BundleRank;
import com.decolatech.easytravel.domain.bundle.enums.BundleStatus;
import com.decolatech.easytravel.domain.bundle.service.BundleService;
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
@RequestMapping("/api/bundles")
@Tag(name = "Bundles", description = "API para gerenciamento de pacotes de viagem")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
public class BundleController {

    @Autowired
    private BundleService bundleService;

    @Operation(summary = "Listar todos os pacotes")
    @GetMapping
    public ResponseEntity<?> getAllBundles() {
        try {
            List<BundleDTO> bundles = bundleService.getAllBundles();
            return ResponseEntity.status(HttpStatus.OK).body(bundles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor: " + e.getMessage()));
        }
    }

    @Operation(summary = "Buscar pacote por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getBundleById(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                .body(bundleService.getBundleById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Resposta(404, "Pacote não encontrado"));
        }
    }

    @Operation(summary = "Criar novo pacote")
    @PostMapping
    public ResponseEntity<?> createBundle(@Valid @RequestBody BundleDTO bundleDTO) {
        try {
            BundleDTO createdBundle = bundleService.createBundle(bundleDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBundle);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, "Erro ao criar pacote: " + e.getMessage()));
        }
    }

    @Operation(summary = "Atualizar pacote")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBundle(@PathVariable Integer id, @Valid @RequestBody BundleDTO bundleDTO) {
        try {
            BundleDTO updatedBundle = bundleService.updateBundle(id, bundleDTO);
            return ResponseEntity.status(HttpStatus.OK).body(updatedBundle);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Resposta(404, "Pacote não encontrado"));
        }
    }

    @Operation(summary = "Deletar pacote")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBundle(@PathVariable Integer id) {
        try {
            bundleService.deleteBundle(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new Resposta(204, "Pacote deletado com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Resposta(404, "Pacote não encontrado"));
        }
    }

    @Operation(summary = "Buscar pacotes por status")
    @GetMapping("/status/{status}")
    public ResponseEntity<?> getBundlesByStatus(@PathVariable BundleStatus status) {
        try {
            List<BundleDTO> bundles = bundleService.getBundlesByStatus(status);
            return ResponseEntity.status(HttpStatus.OK).body(bundles);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor ao buscar pacotes por status: " + e.getMessage()));
        }
    }

    @Operation(summary = "Buscar pacotes por rank")
    @GetMapping("/rank/{rank}")
    public ResponseEntity<?> getBundlesByRank(@PathVariable BundleRank rank) {
        try {
            List<BundleDTO> bundles = bundleService.getBundlesByRank(rank);
            return ResponseEntity.status(HttpStatus.OK).body(bundles);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor ao buscar pacotes por rank: " + e.getMessage()));
        }
    }

    @Operation(summary = "Buscar pacotes disponíveis")
    @GetMapping("/available")
    public ResponseEntity<?> getAvailableBundles() {
        try {
            List<BundleDTO> bundles = bundleService.getAvailableBundles();
            return ResponseEntity.status(HttpStatus.OK).body(bundles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor ao buscar pacotes disponíveis: " + e.getMessage()));
        }
    }

    @Operation(summary = "Buscar pacotes por título")
    @GetMapping("/search")
    public ResponseEntity<?> searchBundlesByTitle(@RequestParam String title) {
        try {
            List<BundleDTO> bundles = bundleService.searchBundlesByTitle(title);
            return ResponseEntity.status(HttpStatus.OK).body(bundles);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor ao buscar pacotes por título: " + e.getMessage()));
        }
    }

    @Operation(summary = "Buscar pacotes por faixa de preço")
    @GetMapping("/price-range")
    public ResponseEntity<?> getBundlesByPriceRange(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice) {
        try {
            List<BundleDTO> bundles = bundleService.getBundlesByPriceRange(minPrice, maxPrice);
            return ResponseEntity.status(HttpStatus.OK).body(bundles);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor ao buscar pacotes por faixa de preço: " + e.getMessage()));
        }
    }

//    @Operation(summary = "Listar todos os pacotes de um usuário")
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<?> getAllBundlesByUser(@PathVariable Integer userId) {
//        try {
//            List<BundleDTO> bundles = bundleService.getAllBundlesByUser(userId);
//            return ResponseEntity.status(HttpStatus.OK).body(bundles);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(new Resposta(500, "Erro interno do servidor: " + e.getMessage()));
//        }
//    }
}
