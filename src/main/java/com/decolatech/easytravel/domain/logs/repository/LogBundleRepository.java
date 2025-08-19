package com.decolatech.easytravel.domain.logs.repository;

import com.decolatech.easytravel.domain.logs.entity.LogBundle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LogBundleRepository extends JpaRepository<LogBundle, Integer> {

    // Buscar por bundle
    List<LogBundle> findByBundleId(Integer bundleId);

    // Buscar por usuário que fez a alteração
    List<LogBundle> findByUserId(Integer userId);

    // Buscar por campo alterado
    List<LogBundle> findByFieldChanged(String fieldChanged);

    // Buscar logs por período
    @Query("SELECT l FROM LogBundle l WHERE l.logDate BETWEEN :startDate AND :endDate")
    List<LogBundle> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Buscar logs ordenados por data
    List<LogBundle> findAllByOrderByLogDateDesc();

    // Buscar logs por bundle e campo
    List<LogBundle> findByBundleIdAndFieldChanged(Integer bundleId, String fieldChanged);

    // Contar alterações por bundle
    @Query("SELECT COUNT(l) FROM LogBundle l WHERE l.bundle.id = :bundleId")
    Long countLogsByBundle(@Param("bundleId") Integer bundleId);
}
