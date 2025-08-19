package com.decolatech.easytravel.domain.booking.dto;

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
@Schema(description = "DTO para transferência de dados de histórico de viagem")
public class TravelHistoryDTO {

    @Schema(hidden = true)
    private Integer id;

    @Schema(example = "5001")
    @NotNull(message = "ID do pagamento é obrigatório")
    private Integer paymentId;

    // Dados completos para exibição
    @Schema(hidden = true)
    private PaymentDTO payment;
}
