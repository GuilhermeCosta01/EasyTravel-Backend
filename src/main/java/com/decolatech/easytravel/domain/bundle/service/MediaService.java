package com.decolatech.easytravel.domain.bundle.service;

import com.decolatech.easytravel.domain.bundle.dto.MediaDTO;
import com.decolatech.easytravel.domain.bundle.entity.Media;
import com.decolatech.easytravel.domain.bundle.entity.Bundle;
import com.decolatech.easytravel.domain.bundle.enums.MediaType;
import com.decolatech.easytravel.domain.bundle.repository.MediaRepository;
import com.decolatech.easytravel.domain.bundle.repository.BundleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private BundleRepository bundleRepository;

    public List<MediaDTO> getAllMedias() {
        return mediaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public MediaDTO getMediaById(Integer id) {
        return mediaRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Mídia não encontrada"));
    }

    public MediaDTO createMedia(MediaDTO mediaDTO) {
        if (mediaDTO.getBundleId() == null) {
            throw new RuntimeException("ID do bundle é obrigatório");
        }

        Bundle bundle = bundleRepository.findById(mediaDTO.getBundleId())
                .orElseThrow(() -> new RuntimeException("Bundle não encontrado"));

        Media media = convertToEntity(mediaDTO);
        media.setBundle(bundle);

        Media savedMedia = mediaRepository.save(media);
        return convertToDTO(savedMedia);
    }

    public MediaDTO updateMedia(Integer id, MediaDTO mediaDTO) {
        Media existingMedia = mediaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mídia não encontrada"));

        boolean updated = false;

        if (mediaDTO.getMediaType() != null && !existingMedia.getMediaType().equals(mediaDTO.getMediaType())) {
            existingMedia.setMediaType(mediaDTO.getMediaType());
            updated = true;
        }

        if (mediaDTO.getMediaUrl() != null && !existingMedia.getMediaUrl().equals(mediaDTO.getMediaUrl())) {
            existingMedia.setMediaUrl(mediaDTO.getMediaUrl());
            updated = true;
        }

        if (mediaDTO.getBundleId() != null && !existingMedia.getBundle().getId().equals(mediaDTO.getBundleId())) {
            Bundle bundle = bundleRepository.findById(mediaDTO.getBundleId())
                    .orElseThrow(() -> new RuntimeException("Bundle não encontrado"));
            existingMedia.setBundle(bundle);
            updated = true;
        }

        if (!updated) {
            throw new IllegalArgumentException("Nenhuma alteração foi detectada na mídia");
        }

        Media savedMedia = mediaRepository.save(existingMedia);
        return convertToDTO(savedMedia);
    }

    public void deleteMedia(Integer id) {
        if (!mediaRepository.existsById(id)) {
            throw new RuntimeException("Mídia não encontrada");
        }
        mediaRepository.deleteById(id);
    }

    public List<MediaDTO> getMediasByBundle(Integer bundleId) {
        if (bundleId == null) {
            throw new IllegalArgumentException("ID do bundle não pode ser nulo");
        }
        return mediaRepository.findByBundleId(bundleId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MediaDTO> getMediasByType(MediaType type) {
        if (type == null) {
            throw new IllegalArgumentException("Tipo de mídia não pode ser nulo");
        }
        return mediaRepository.findByMediaType(type).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MediaDTO> getVideosByBundle(Integer bundleId) {
        if (bundleId == null) {
            throw new IllegalArgumentException("ID do bundle não pode ser nulo");
        }
        return mediaRepository.findVideosByBundle(bundleId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MediaDTO> getImagesByBundle(Integer bundleId) {
        if (bundleId == null) {
            throw new IllegalArgumentException("ID do bundle não pode ser nulo");
        }
        return mediaRepository.findImagesByBundle(bundleId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private MediaDTO convertToDTO(Media media) {
        MediaDTO dto = new MediaDTO();
        dto.setId(media.getId());
        dto.setMediaType(media.getMediaType());
        dto.setMediaUrl(media.getMediaUrl());
        dto.setBundleId(media.getBundle().getId());
        return dto;
    }

    private Media convertToEntity(MediaDTO dto) {
        Media media = new Media();
        media.setId(dto.getId());
        media.setMediaType(dto.getMediaType());
        media.setMediaUrl(dto.getMediaUrl());
        return media;
    }
}
