package com.decolatech.easytravel.domain.bundle.repository;

import com.decolatech.easytravel.domain.bundle.entity.BundleLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BundleLocationRepository extends JpaRepository<BundleLocation, Integer> {

    // Buscar por bundle
    List<BundleLocation> findByBundleId(Integer bundleId);

    // Buscar por destino
    List<BundleLocation> findByDestinationId(Integer destinationId);

    // Buscar por partida
    List<BundleLocation> findByDepartureId(Integer departureId);

    // Buscar pacotes que saem de uma localização específica
    @Query("SELECT bl FROM BundleLocation bl WHERE bl.departure.id = :departureId")
    List<BundleLocation> findBundlesByDeparture(@Param("departureId") Integer departureId);

    // Buscar pacotes que vão para um destino específico
    @Query("SELECT bl FROM BundleLocation bl WHERE bl.destination.id = :destinationId")
    List<BundleLocation> findBundlesByDestination(@Param("destinationId") Integer destinationId);

    // Buscar pacotes por rota (partida e destino)
    @Query("SELECT bl FROM BundleLocation bl WHERE bl.departure.id = :departureId AND bl.destination.id = :destinationId")
    List<BundleLocation> findBundlesByRoute(@Param("departureId") Integer departureId, @Param("destinationId") Integer destinationId);
}
