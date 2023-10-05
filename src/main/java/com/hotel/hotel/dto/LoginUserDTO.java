package com.hotel.hotel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Schema(description = "dto para el login del usuario")
public class LoginUserDTO {
    @Schema(description = "Email del usuario", example = "fernando@gmail.com")
    private String email;
    @Schema(description = "contrasena del usuario", example = "123456789")
    private String password;
}
