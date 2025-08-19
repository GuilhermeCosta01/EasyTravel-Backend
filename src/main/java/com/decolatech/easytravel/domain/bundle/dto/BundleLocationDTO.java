package com.decolatech.easytravel.domain.bundle.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
@Schema(description = "DTO para transferência de dados de localização do pacote")
public class BundleLocationDTO {

    @Schema(hidden = true)
    private Integer id;

    @Schema(description = "ID do pacote")
    @NotNull(message = "ID do pacote é obrigatório")
    private Integer bundleId;

    @Schema(description = "ID do destino")
    @NotNull(message = "ID do destino é obrigatório")
    private Integer destinationId;

    @Schema(description = "ID da partida")
    @NotNull(message = "ID da partida é obrigatório")
    private Integer departureId;

    @Schema(hidden = true)
    private LocationDTO destination;
    @Schema(hidden = true)
    private LocationDTO departure;
}
