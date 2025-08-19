package com.decolatech.easytravel.domain.logs.service;

import com.decolatech.easytravel.domain.logs.dto.LogUserDTO;
import com.decolatech.easytravel.domain.logs.entity.LogUser;
import com.decolatech.easytravel.domain.logs.repository.LogUserRepository;
import com.decolatech.easytravel.domain.user.entity.User;
import com.decolatech.easytravel.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogUserService {

    @Autowired
    private LogUserRepository logUserRepository;

    @Autowired
    private UserRepository userRepository;

    public List<LogUserDTO> getAllLogUsers() {
        return logUserRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public LogUserDTO getLogUserById(Integer id) {
        return logUserRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Log de usuário não encontrado"));
    }

    public LogUserDTO createLogUser(LogUserDTO logUserDTO) {
        if (logUserDTO.getUserTargetId() == null) {
            throw new RuntimeException("ID do usuário alvo é obrigatório");
        }

        User userTarget = userRepository.findById(logUserDTO.getUserTargetId())
                .orElseThrow(() -> new RuntimeException("Usuário alvo não encontrado"));

        User userAction = null;
        if (logUserDTO.getUserActionId() != null) {
            userAction = userRepository.findById(logUserDTO.getUserActionId())
                    .orElseThrow(() -> new RuntimeException("Usuário que fez a ação não encontrado"));
        }

        LogUser logUser = convertToEntity(logUserDTO);
        logUser.setUserTarget(userTarget);
        logUser.setUserAction(userAction);
        logUser.setLogDate(LocalDateTime.now());

        LogUser savedLogUser = logUserRepository.save(logUser);
        return convertToDTO(savedLogUser);
    }

    public void deleteLogUser(Integer id) {
        if (!logUserRepository.existsById(id)) {
            throw new RuntimeException("Log de usuário não encontrado");
        }
        logUserRepository.deleteById(id);
    }

    public List<LogUserDTO> getLogUsersByTarget(Integer userTargetId) {
        if (userTargetId == null) {
            throw new IllegalArgumentException("ID do usuário alvo não pode ser nulo");
        }
        return logUserRepository.findByUserTargetId(userTargetId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<LogUserDTO> getLogUsersByAction(Integer userActionId) {
        if (userActionId == null) {
            throw new IllegalArgumentException("ID do usuário de ação não pode ser nulo");
        }
        return logUserRepository.findByUserActionId(userActionId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<LogUserDTO> getLogUsersByField(String fieldChanged) {
        if (fieldChanged == null || fieldChanged.trim().isEmpty()) {
            throw new IllegalArgumentException("Campo alterado não pode ser nulo ou vazio");
        }
        return logUserRepository.findByFieldChanged(fieldChanged.trim()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<LogUserDTO> getLogUsersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("Data inicial não pode ser nula");
        }
        if (endDate == null) {
            throw new IllegalArgumentException("Data final não pode ser nula");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Data inicial não pode ser posterior à data final");
        }
        return logUserRepository.findByDateRange(startDate, endDate).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private LogUserDTO convertToDTO(LogUser logUser) {
        LogUserDTO dto = new LogUserDTO();
        dto.setId(logUser.getId());
        dto.setLogDate(logUser.getLogDate());
        dto.setUserTargetId(logUser.getUserTarget().getId());
        dto.setUserActionId(logUser.getUserAction() != null ? logUser.getUserAction().getId() : null);
        dto.setFieldChanged(logUser.getFieldChanged());
        dto.setOldValue(logUser.getOldValue());
        dto.setNewValue(logUser.getNewValue());
        dto.setUserTargetName(logUser.getUserTarget().getUsername());
        dto.setUserActionName(logUser.getUserAction() != null ? logUser.getUserAction().getUsername() : null);
        return dto;
    }

    private LogUser convertToEntity(LogUserDTO dto) {
        LogUser logUser = new LogUser();
        logUser.setId(dto.getId());
        logUser.setFieldChanged(dto.getFieldChanged());
        logUser.setOldValue(dto.getOldValue());
        logUser.setNewValue(dto.getNewValue());
        return logUser;
    }
}
