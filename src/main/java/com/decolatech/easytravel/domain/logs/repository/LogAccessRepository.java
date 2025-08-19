package com.decolatech.easytravel.domain.logs.repository;

import com.decolatech.easytravel.domain.logs.entity.LogAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LogAccessRepository extends JpaRepository<LogAccess, Integer> {

    // Buscar por usuário
    List<LogAccess> findByUserId(Integer userId);

    // Buscar por tipo de ação
    List<LogAccess> findByActionType(String actionType);

    // Buscar logs por período
    @Query("SELECT l FROM LogAccess l WHERE l.logDate BETWEEN :startDate AND :endDate")
    List<LogAccess> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Buscar logs ordenados por data
    List<LogAccess> findAllByOrderByLogDateDesc();

    // Buscar logs por usuário e tipo de ação
    List<LogAccess> findByUserIdAndActionType(Integer userId, String actionType);

    // Buscar logs por descrição
    @Query("SELECT l FROM LogAccess l WHERE l.description LIKE %:description%")
    List<LogAccess> findByDescriptionContaining(@Param("description") String description);

    // Contar acessos por usuário
    @Query("SELECT COUNT(l) FROM LogAccess l WHERE l.user.id = :userId")
    Long countAccessByUser(@Param("userId") Integer userId);

    // Contar acessos por tipo
    @Query("SELECT COUNT(l) FROM LogAccess l WHERE l.actionType = :actionType")
    Long countAccessByActionType(@Param("actionType") String actionType);
}
