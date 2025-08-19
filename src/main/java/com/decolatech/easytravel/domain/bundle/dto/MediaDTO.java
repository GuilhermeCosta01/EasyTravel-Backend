package com.decolatech.easytravel.domain.bundle.dto;

import com.decolatech.easytravel.domain.bundle.enums.MediaType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
@Schema(description = "DTO para transferência de dados de mídia")
public class MediaDTO {

    @Schema(hidden = true)
    private Integer id;

    @Schema(example = "IMAGE")
    @NotNull(message = "Tipo de mídia é obrigatório")
    private MediaType mediaType;

    @Schema(example = "https://example.com/imagem.jpg")
    @NotBlank(message = "URL da mídia é obrigatória")
    private String mediaUrl;

    @Schema(example = "4001")
    @NotNull(message = "ID do pacote é obrigatório")
    private Integer bundleId;
}
