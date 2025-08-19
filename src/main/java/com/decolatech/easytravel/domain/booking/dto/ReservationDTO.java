package com.decolatech.easytravel.domain.booking.dto;

import com.decolatech.easytravel.domain.booking.enums.ReservationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
@Schema(description = "DTO para transferência de dados de reserva")
public class ReservationDTO {

    @Schema(hidden = true)
    private Integer id;
    
    @Schema(hidden = true, accessMode = Schema.AccessMode.READ_ONLY)
    private ReservationStatus reservStatus;
    
    @Schema(example = "2023-07-23T10:15:30")
    @NotNull(message = "Data da reserva é obrigatória")
    private LocalDateTime reservDate;

    @Schema(example = "501")
    @NotNull(message = "ID do usuário é obrigatório")
    private Integer userId;

    @Schema(example = "4001")
    @NotNull(message = "ID do pacote é obrigatório")
    private Integer bundleId;

    @Schema(hidden = true)
    private String userName;
    @Schema(hidden = true)
    private String bundleTitle;

    @Schema(description = "Informações completas do pacote")
    private com.decolatech.easytravel.domain.bundle.dto.BundleDTO bundle;
}
