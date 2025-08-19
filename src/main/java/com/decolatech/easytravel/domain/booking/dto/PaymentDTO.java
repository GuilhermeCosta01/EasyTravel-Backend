package com.decolatech.easytravel.domain.booking.dto;

import com.decolatech.easytravel.domain.booking.enums.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
@Schema(description = "DTO para transferência de dados de pagamento")
public class PaymentDTO {

    @Schema(hidden = true)
    private Integer id;

    @Schema(example = "2023-07-23T10:15:30")
    @NotNull(message = "Data do pagamento é obrigatória")
    private LocalDateTime paymentDate;

    @Schema(example = "CREDIT")
    @NotNull(message = "Método de pagamento é obrigatório")
    private PaymentMethod paymentMethod;

    @Schema(example = "3200")
    @NotNull(message = "valor total é obrigatória")
    private Double totPrice;

    @Schema(example = "1", description = "Número de parcelas (apenas para pagamento com cartão de crédito)")
    private Integer installments;

    @Schema(example = "1001")
    @NotNull(message = "ID da reserva é obrigatório")
    private Integer reservationId;


}
