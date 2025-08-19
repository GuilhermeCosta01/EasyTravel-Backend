package com.decolatech.easytravel.domain.logs.service;

import com.decolatech.easytravel.domain.logs.dto.LogAccessDTO;
import com.decolatech.easytravel.domain.logs.entity.LogAccess;
import com.decolatech.easytravel.domain.logs.repository.LogAccessRepository;
import com.decolatech.easytravel.domain.user.entity.User;
import com.decolatech.easytravel.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogAccessService {

    @Autowired
    private LogAccessRepository logAccessRepository;

    @Autowired
    private UserRepository userRepository;

    public List<LogAccessDTO> getAllLogAccess() {
        return logAccessRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public LogAccessDTO getLogAccessById(Integer id) {
        return logAccessRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Log de acesso não encontrado"));
    }

    public LogAccessDTO createLogAccess(LogAccessDTO logAccessDTO) {
        User user = null;
        if (logAccessDTO.getUserId() != null) {
            user = userRepository.findById(logAccessDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        }

        LogAccess logAccess = convertToEntity(logAccessDTO);
        logAccess.setUser(user);
        logAccess.setLogDate(LocalDateTime.now());

        LogAccess savedLogAccess = logAccessRepository.save(logAccess);
        return convertToDTO(savedLogAccess);
    }

    public void deleteLogAccess(Integer id) {
        if (!logAccessRepository.existsById(id)) {
            throw new RuntimeException("Log de acesso não encontrado");
        }
        logAccessRepository.deleteById(id);
    }

    public List<LogAccessDTO> getLogAccessByUser(Integer userId) {
        if (userId == null) {
            throw new IllegalArgumentException("ID do usuário não pode ser nulo");
        }
        return logAccessRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<LogAccessDTO> getLogAccessByActionType(String actionType) {
        if (actionType == null || actionType.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo de ação não pode ser nulo ou vazio");
        }
        return logAccessRepository.findByActionType(actionType.trim()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<LogAccessDTO> getLogAccessByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("Data inicial não pode ser nula");
        }
        if (endDate == null) {
            throw new IllegalArgumentException("Data final não pode ser nula");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Data inicial não pode ser posterior à data final");
        }
        return logAccessRepository.findByDateRange(startDate, endDate).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private LogAccessDTO convertToDTO(LogAccess logAccess) {
        LogAccessDTO dto = new LogAccessDTO();
        dto.setId(logAccess.getId());
        dto.setLogDate(logAccess.getLogDate());
        dto.setUserId(logAccess.getUser() != null ? logAccess.getUser().getId() : null);
        dto.setActionType(logAccess.getActionType());
        dto.setDescription(logAccess.getDescription());
        dto.setUserName(logAccess.getUser() != null ? logAccess.getUser().getUsername() : null);
        return dto;
    }

    private LogAccess convertToEntity(LogAccessDTO dto) {
        LogAccess logAccess = new LogAccess();
        logAccess.setId(dto.getId());
        logAccess.setActionType(dto.getActionType());
        logAccess.setDescription(dto.getDescription());
        return logAccess;
    }
}
