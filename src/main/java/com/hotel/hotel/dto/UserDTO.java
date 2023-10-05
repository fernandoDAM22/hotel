package com.hotel.hotel.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@Schema(description = "dto con los datos a enviar de un usuario")
public class UserDTO {
    @Schema(description = "Id del usuario", example = "1")
    private Long id;

    @NotEmpty(message = "error.user.nombre_vacio")
    @Size(max = 50,message = "error.user.nombre")
    @Column(name = "name")
    @Schema(description = "nombre del usuario", example = "Fernando")
    private String name;

    @NotEmpty(message = "error.user.email_vacio")
    @Email(message = "error.user.email")
    @Size(max = 255)
    @Column(name = "email")
    @Schema(description = "email del usuario", example = "fernando@gmail.com")
    private String email;
}
