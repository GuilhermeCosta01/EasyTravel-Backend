package com.decolatech.easytravel.domain.bundle.controller;

import com.decolatech.easytravel.config.security.SecurityConfiguration;
import com.decolatech.easytravel.common.Resposta;
import com.decolatech.easytravel.domain.bundle.dto.MediaDTO;
import com.decolatech.easytravel.domain.bundle.service.MediaService;
import com.decolatech.easytravel.domain.bundle.enums.MediaType;
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
@RequestMapping("/api/medias")
@Tag(name = "Medias", description = "API para gerenciamento de mídias")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
public class MediaController {

    @Autowired
    private MediaService mediaService;

    @Operation(summary = "Listar todas as mídias")
    @GetMapping
    public ResponseEntity<?> getAllMedias() {
        try {
            List<MediaDTO> medias = mediaService.getAllMedias();
            return ResponseEntity.status(HttpStatus.OK).body(medias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor: " + e.getMessage()));
        }
    }

    @Operation(summary = "Buscar mídia por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getMediaById(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                .body(mediaService.getMediaById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Resposta(404, "Mídia não encontrada"));
        }
    }

    @Operation(summary = "Criar nova mídia")
    @PostMapping
    public ResponseEntity<?> createMedia(@Valid @RequestBody MediaDTO mediaDTO) {
        try {
            MediaDTO createdMedia = mediaService.createMedia(mediaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdMedia);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, "Erro ao criar mídia: " + e.getMessage()));
        }
    }

    @Operation(summary = "Atualizar mídia")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMedia(@PathVariable Integer id, @Valid @RequestBody MediaDTO mediaDTO) {
        try {
            MediaDTO updatedMedia = mediaService.updateMedia(id, mediaDTO);
            return ResponseEntity.status(HttpStatus.OK).body(updatedMedia);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Resposta(404, "Mídia não encontrada"));
        }
    }

    @Operation(summary = "Deletar mídia")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMedia(@PathVariable Integer id) {
        try {
            mediaService.deleteMedia(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new Resposta(204, "Mídia deletada com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Resposta(404, "Mídia não encontrada"));
        }
    }

    @Operation(summary = "Buscar mídias por bundle")
    @GetMapping("/bundle/{bundleId}")
    public ResponseEntity<?> getMediasByBundle(@PathVariable Integer bundleId) {
        try {
            List<MediaDTO> medias = mediaService.getMediasByBundle(bundleId);
            return ResponseEntity.status(HttpStatus.OK).body(medias);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor ao buscar mídias por bundle: " + e.getMessage()));
        }
    }

    @Operation(summary = "Buscar mídias por tipo")
    @GetMapping("/type/{type}")
    public ResponseEntity<?> getMediasByType(@PathVariable MediaType type) {
        try {
            List<MediaDTO> medias = mediaService.getMediasByType(type);
            return ResponseEntity.status(HttpStatus.OK).body(medias);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor ao buscar mídias por tipo: " + e.getMessage()));
        }
    }

    @Operation(summary = "Buscar vídeos por bundle")
    @GetMapping("/videos/bundle/{bundleId}")
    public ResponseEntity<?> getVideosByBundle(@PathVariable Integer bundleId) {
        try {
            List<MediaDTO> videos = mediaService.getVideosByBundle(bundleId);
            return ResponseEntity.status(HttpStatus.OK).body(videos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor ao buscar vídeos por bundle: " + e.getMessage()));
        }
    }

    @Operation(summary = "Buscar imagens por bundle")
    @GetMapping("/images/bundle/{bundleId}")
    public ResponseEntity<?> getImagesByBundle(@PathVariable Integer bundleId) {
        try {
            List<MediaDTO> images = mediaService.getImagesByBundle(bundleId);
            return ResponseEntity.status(HttpStatus.OK).body(images);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Resposta(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Resposta(500, "Erro interno do servidor ao buscar imagens por bundle: " + e.getMessage()));
        }
    }
}
