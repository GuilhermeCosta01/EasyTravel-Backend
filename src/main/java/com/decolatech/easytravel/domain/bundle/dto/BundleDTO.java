package com.decolatech.easytravel.domain.bundle.dto;

import com.decolatech.easytravel.domain.bundle.enums.BundleRank;
import com.decolatech.easytravel.domain.bundle.enums.BundleStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
@Schema(description = "DTO para transferência de dados de pacotes")
public class BundleDTO {

    @Schema(hidden = true)
    private Integer id;

    @Schema(example = "Pacote Férias Nordeste")
    @NotBlank(message = "Título do pacote é obrigatório")
    @Size(max = 100, message = "Título deve ter no máximo 100 caracteres")
    private String bundleTitle;

    @Schema(example = "Pacote para o nordeste com tudo incluso")
    @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
    private String bundleDescription;

    @Schema(example = "1200.00")
    @NotNull(message = "Preço inicial é obrigatório")
    @Positive(message = "Preço inicial deve ser positivo")
    private Double initialPrice;

    @Schema(example = "BRONZE")
    @NotNull(message = "Rank do pacote é obrigatório")
    private BundleRank bundleRank;

    @NotNull(message = "Data inicial é obrigatória")
    private LocalDateTime initialDate;

    @NotNull(message = "Data final é obrigatória")
    private LocalDateTime finalDate;

    @NotNull(message = "Quantidade é obrigatória")
    @Positive(message = "Quantidade deve ser positiva")
    private Integer quantity;

    @NotNull(message = "Número de viajantes é obrigatório")
    @Positive(message = "Número de viajantes deve ser positivo")
    private Integer travelersNumber;

    @NotNull(message = "Status do pacote é obrigatório")
    private BundleStatus bundleStatus;
}
