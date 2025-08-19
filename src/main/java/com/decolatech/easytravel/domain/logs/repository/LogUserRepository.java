package com.decolatech.easytravel.domain.logs.repository;

import com.decolatech.easytravel.domain.logs.entity.LogUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LogUserRepository extends JpaRepository<LogUser, Integer> {

    // Buscar por usuário alvo
    List<LogUser> findByUserTargetId(Integer userTargetId);

    // Buscar por usuário que fez a ação
    List<LogUser> findByUserActionId(Integer userActionId);

    // Buscar por campo alterado
    List<LogUser> findByFieldChanged(String fieldChanged);

    // Buscar logs por período
    @Query("SELECT l FROM LogUser l WHERE l.logDate BETWEEN :startDate AND :endDate")
    List<LogUser> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Buscar logs ordenados por data
    List<LogUser> findAllByOrderByLogDateDesc();

    // Buscar logs por usuário alvo e campo
    List<LogUser> findByUserTargetIdAndFieldChanged(Integer userTargetId, String fieldChanged);

    // Contar alterações por usuário
    @Query("SELECT COUNT(l) FROM LogUser l WHERE l.userTarget.id = :userTargetId")
    Long countLogsByUserTarget(@Param("userTargetId") Integer userTargetId);
}
