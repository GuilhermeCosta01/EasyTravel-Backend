package com.decolatech.easytravel.domain.bundle.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
@Schema(description = "DTO para transferência de dados de localização")
public class LocationDTO {

    @Schema(hidden = true)
    private Integer id;

    @Schema(example = "BR")
    @NotBlank(message = "País é obrigatório")
    @Size(max = 5, message = "País deve ter no máximo 5 caracteres")
    private String country;

    @Schema(example = "SP")
    @NotBlank(message = "Estado é obrigatório")
    @Size(max = 2, message = "Estado deve ter no máximo 2 caracteres")
    private String states;

    @Schema(example = "São Paulo")
    @NotBlank(message = "Cidade é obrigatória")
    @Size(max = 50, message = "Cidade deve ter no máximo 50 caracteres")
    private String city;
}
