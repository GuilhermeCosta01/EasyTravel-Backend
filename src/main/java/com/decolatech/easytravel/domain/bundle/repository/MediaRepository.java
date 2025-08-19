package com.decolatech.easytravel.domain.bundle.repository;

import com.decolatech.easytravel.domain.bundle.entity.Media;
import com.decolatech.easytravel.domain.bundle.enums.MediaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Media, Integer> {

    // Buscar por bundle
    List<Media> findByBundleId(Integer bundleId);

    // Buscar por tipo de mídia
    List<Media> findByMediaType(MediaType mediaType);

    // Buscar vídeos por bundle
    @Query("SELECT m FROM Media m WHERE m.bundle.id = :bundleId AND m.mediaType = 1")
    List<Media> findVideosByBundle(@Param("bundleId") Integer bundleId);

    // Buscar imagens por bundle
    @Query("SELECT m FROM Media m WHERE m.bundle.id = :bundleId AND m.mediaType = 0")
    List<Media> findImagesByBundle(@Param("bundleId") Integer bundleId);

    // Buscar por bundle e tipo
    List<Media> findByBundleIdAndMediaType(Integer bundleId, MediaType mediaType);

    // Contar mídias por bundle
    @Query("SELECT COUNT(m) FROM Media m WHERE m.bundle.id = :bundleId")
    Long countByBundleId(@Param("bundleId") Integer bundleId);
}
