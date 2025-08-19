package com.decolatech.easytravel.domain.user.dto;

import com.decolatech.easytravel.domain.user.enums.UserRole;
import com.decolatech.easytravel.domain.user.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
//comentario para um push teste

@Getter
@Setter
@ToString(exclude = {"password"})
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para transferência de dados de usuário")
public class UserDTO {

    @Schema(hidden = true)
    private Integer id;

    @Schema(example = "jorge.oliveira")
    @NotBlank(message = "Name é obrigatório")
    @Size(max = 100, message = "Name deve ter no máximo 100 caracteres")
    private String name;


    @Schema(example = "jorge.oliveira@gmail.com")
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter formato válido")
    @Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
    private String email;

    @Schema(example = "12345678909")
    @NotBlank(message = "CPF é obrigatório")
    @Size(min = 11, max = 11, message = "CPF deve ter 11 caracteres")
    private String cpf;

    @Schema(example = "1234567890")
    @Size(max = 10, message = "Passport deve ter no máximo 10 caracteres")
    private String passport;

    @Schema(example = "senhaSegura123")
    @Size(min = 6, message = "Password deve ter no mínimo 6 caracteres")
    private String password;

    @Schema(example = "+55 11 91234-5678")
    @NotBlank(message = "Telephone é obrigatório")
    @Size(max = 18, message = "Telephone deve ter no máximo 18 caracteres")
    private String telephone;

    @Schema(example = "ACTIVATED")
    @NotNull(message = "User status é obrigatório")
    private UserStatus userStatus;

    @Schema(example = "USER")
    @NotNull(message = "User role é obrigatório")
    private UserRole userRole;
}
