package com.decolatech.easytravel.domain.user.repository;

import com.decolatech.easytravel.domain.user.entity.User;
import com.decolatech.easytravel.domain.user.enums.UserRole;
import com.decolatech.easytravel.domain.user.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Buscar por email
    User findByEmail(String email);

    // Buscar por CPF
    User findByCpf(String cpf);

    // Buscar por passport
    User findByPassport(String passport);

    // Buscar por status
    List<User> findByUserStatus(UserStatus userStatus);

    // Buscar por role
    List<User> findByUserRole(UserRole userRole);

    // Buscar por name
    User findByName(String name);

    // Buscar usuários por role e status
    @Query("SELECT u FROM User u WHERE u.userRole = :role AND u.userStatus = :status")
    List<User> findByRoleAndStatus(@Param("role") UserRole role, @Param("status") UserStatus status);

    // Verificar se email já existe
    boolean existsByEmail(String email);

    // Verificar se CPF já existe
    boolean existsByCpf(String cpf);

    // Verificar se passport já existe
    boolean existsByPassport(String passport);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    UserDetails findByLoginEmail(@Param("email") String email);
}
