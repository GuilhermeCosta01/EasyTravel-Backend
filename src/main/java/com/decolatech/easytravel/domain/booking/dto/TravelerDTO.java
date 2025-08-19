package com.decolatech.easytravel.domain.booking.dto;

import com.decolatech.easytravel.domain.booking.enums.DocumentType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para transferência de dados de viajante")
public class TravelerDTO {

    @Schema(hidden = true)
    private Integer id;

    @Schema(example = "Maria Souza")
    @NotBlank(message = "Nome completo é obrigatório")
    @Size(max = 100, message = "Nome completo deve ter no máximo 100 caracteres")
    private String fullName;

    @Schema(example = "1234567890")
    @NotBlank(message = "Número do documento é obrigatório")
    @Size(max = 20, message = "Número do documento deve ter no máximo 20 caracteres")
    private String documentNumber;

    @Schema(example = "CPF")
    @NotNull(message = "Tipo de documento é obrigatório")
    private DocumentType documentType;

    @Schema(example = "28")
    @NotNull(message = "Idade é obrigatória")
    @Min(value = 18, message = "Idade deve ser no mínimo 18 anos")
    private Integer age;


    //@NotNull(message = "ID da reserva é obrigatório")

    private Integer reservationId;
}
