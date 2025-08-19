package com.decolatech.easytravel.domain.bundle.repository;

import com.decolatech.easytravel.domain.bundle.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

    // Buscar por país
    List<Location> findByCountry(String country);

    // Buscar por estado
    List<Location> findByStates(String states);

    // Buscar por cidade
    List<Location> findByCity(String city);

    // Buscar por país e estado
    List<Location> findByCountryAndStates(String country, String states);

    // Buscar por país, estado e cidade
    Optional<Location> findByCountryAndStatesAndCity(String country, String states, String city);

    // Buscar localizações por cidade (case insensitive)
    @Query("SELECT l FROM Location l WHERE LOWER(l.city) LIKE LOWER(CONCAT('%', :city, '%'))")
    List<Location> findByCityContainingIgnoreCase(@Param("city") String city);

    // Verificar se localização já existe
    boolean existsByCountryAndStatesAndCity(String country, String states, String city);
}
