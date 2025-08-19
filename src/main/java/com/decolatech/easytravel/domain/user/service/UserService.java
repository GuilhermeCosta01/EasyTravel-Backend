package com.decolatech.easytravel.domain.user.service;

import com.decolatech.easytravel.domain.user.dto.UserDTO;
import com.decolatech.easytravel.domain.user.entity.User;
import com.decolatech.easytravel.domain.user.enums.UserRole;
import com.decolatech.easytravel.domain.user.enums.UserStatus;
import com.decolatech.easytravel.domain.user.exception.CpfAlreadyExistsException;
import com.decolatech.easytravel.domain.user.exception.EmailAlreadyExistsException;
import com.decolatech.easytravel.domain.user.exception.PassportAlreadyExistsException;
import com.decolatech.easytravel.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserById(Integer id) {
        return userRepository.findById(id)
                .map(this::convertToDTO);
    }

    public UserDTO createUser(UserDTO userDTO) {
        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password é obrigatório para criação de usuário");
        }
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new EmailAlreadyExistsException(userDTO.getEmail());
        }
        if (userRepository.existsByCpf(userDTO.getCpf())) {
            throw new CpfAlreadyExistsException(userDTO.getCpf());
        }
        if (userRepository.existsByPassport(userDTO.getPassport())) {
            throw new PassportAlreadyExistsException(userDTO.getPassport());
        }

        User user = convertToEntity(userDTO);
        user.setPassword(userDTO.getPassword());

        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    public UserDTO updateUser(Integer id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        boolean updated = false;

        if (!existingUser.getName().equals(userDTO.getName())) updated = true;
        if (!existingUser.getEmail().equals(userDTO.getEmail())) updated = true;
        if (!existingUser.getTelephone().equals(userDTO.getTelephone())) updated = true;
        if (!existingUser.getUserStatus().equals(userDTO.getUserStatus())) updated = true;
        if (!existingUser.getUserRole().equals(userDTO.getUserRole())) updated = true;
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            if (!userDTO.getPassword().equals(existingUser.getPassword())) updated = true;
        }

        if (!updated) {
            throw new IllegalArgumentException("Nenhuma alteração foi detectada nos dados do usuário");
        }

        existingUser.setName(userDTO.getName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setTelephone(userDTO.getTelephone());
        existingUser.setUserStatus(userDTO.getUserStatus());
        existingUser.setUserRole(userDTO.getUserRole());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(userDTO.getPassword());
        }

        User savedUser = userRepository.save(existingUser);
        return convertToDTO(savedUser);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public List<UserDTO> getUsersByRole(UserRole role) {
        List<UserDTO> users = userRepository.findByUserRole(role).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        if (users.isEmpty()) {
            throw new IllegalArgumentException("Nenhum usuário encontrado com o role especificado");
        }

        return users;
    }

    public List<UserDTO> getUsersByStatus(UserStatus status) {
        List<UserDTO> users = userRepository.findByUserStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        if (users.isEmpty()) {
            throw new IllegalArgumentException("Nenhum usuário encontrado com o status especificado");
        }

        return users;
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setCpf(user.getCpf());
        dto.setPassport(user.getPassport());
        dto.setTelephone(user.getTelephone());
        dto.setUserStatus(user.getUserStatus());
        dto.setUserRole(user.getUserRole());
        return dto;
    }

    private User convertToEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setCpf(dto.getCpf());
        user.setPassport(dto.getPassport());
        user.setPassword(dto.getPassword());
        user.setTelephone(dto.getTelephone());
        user.setUserStatus(dto.getUserStatus() != null ? dto.getUserStatus() : UserStatus.ACTIVATED);
        user.setUserRole(dto.getUserRole());
        return user;
    }
}
