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
@Schema(description = "DTO para transferência de dados de log de bundle")
public class LogBundleDTO {

    @Schema(hidden = true)
    private Integer id;

    @Schema(example = "2023-07-23T10:15:30")
    @NotNull(message = "Data do log é obrigatória")
    private LocalDateTime logDate;

    @Schema(example = "4001")
    @NotNull(message = "ID do bundle é obrigatório")
    private Integer bundleId;

    @Schema(example = "preço")
    @NotBlank(message = "Campo alterado é obrigatório")
    @Size(max = 50, message = "Campo alterado deve ter no máximo 50 caracteres")
    private String fieldChanged;

    @Schema(example = "1000.00")
    @Size(max = 255, message = "Valor antigo deve ter no máximo 255 caracteres")
    private String oldValue;

    @Schema(example = "1200.00")
    @Size(max = 255, message = "Valor novo deve ter no máximo 255 caracteres")
    private String newValue;

    @Schema(example = "501")
    private Integer userId;

    // Dados completos para exibição
    private String bundleTitle;
    private String userName;
}
