package com.decolatech.easytravel.domain.user.dto;


import com.decolatech.easytravel.domain.user.enums.UserRole;

public record RegisterDTO(String name, String email, String cpf,String passport, String password, String telephone, UserRole role) {

}


