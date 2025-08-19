package com.decolatech.easytravel.domain.booking.dto;

import com.decolatech.easytravel.domain.booking.enums.Rating;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "DTO para transferência de dados de avaliação")
public class ReviewDTO {

    @Schema(hidden = true)
    private Integer id;

    @Schema(example = "FIVE_STARS")
    @NotNull(message = "Avaliação é obrigatória")
    private Rating rating;

    @Schema(example = "Ótima experiência!")
    @Size(max = 100, message = "Comentário deve ter no máximo 100 caracteres")
    private String comment;

    @Schema(example = "2023-07-23T10:15:30")
    @NotNull(message = "Data da avaliação é obrigatória")
    private LocalDateTime avaliationDate;


    //@NotNull(message = "ID do histórico de viagem é obrigatório")
    private Integer travelHistoryId;


    //@NotNull(message = "ID do pacote é obrigatório")
    private Integer bundleId;
}
