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
@Schema(description = "DTO para transferência de dados de log de acesso")
public class LogAccessDTO {

    @Schema(hidden = true)
    private Integer id;

    @Schema(example = "2023-07-23T10:15:30")
    @NotNull(message = "Data do log é obrigatória")
    private LocalDateTime logDate;

    @Schema(example = "501")
    private Integer userId;

    @Schema(example = "LOGIN")
    @NotBlank(message = "Tipo de ação é obrigatório")
    @Size(max = 50, message = "Tipo de ação deve ter no máximo 50 caracteres")
    private String actionType;

    @Schema(example = "Usuário realizou login com sucesso")
    @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
    private String description;

    @Schema(example = "João Silva")
    private String userName;
}
