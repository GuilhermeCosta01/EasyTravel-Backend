package com.decolatech.easytravel.domain.logs.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para transferência de dados de log de usuário")
public class LogUserDTO {

    @Schema(hidden = true)
    private Integer id;

    @Schema(example = "2023-07-23T10:15:30")
    @NotNull(message = "Data do log é obrigatória")
    private LocalDateTime logDate;

    @Schema(example = "101")
    @NotNull(message = "ID do usuário alvo é obrigatório")
    private Integer userTargetId;

    @Schema(example = "202")
    private Integer userActionId;

    @Schema(example = "email")
    @NotBlank(message = "Campo alterado é obrigatório")
    @Size(max = 50, message = "Campo alterado deve ter no máximo 50 caracteres")
    private String fieldChanged;

    @Schema(example = "old@email.com")
    @Size(max = 255, message = "Valor antigo deve ter no máximo 255 caracteres")
    private String oldValue;

    @Schema(example = "new@email.com")
    @Size(max = 255, message = "Valor novo deve ter no máximo 255 caracteres")
    private String newValue;

    // Dados completos para exibição
    private String userTargetName;
    private String userActionName;
}
