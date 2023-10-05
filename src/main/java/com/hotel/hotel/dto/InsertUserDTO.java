package com.hotel.hotel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Schema(description = "DTO para insertar un usuario")
public class InsertUserDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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
    @Schema(description = "Email del usuario", example = "fernando@gmail.com")
    private String email;

    @NotEmpty(message = "error.user.password_vacia")
    @Size(min = 8, message = "error.user.password")
    @Column(name = "password")
    @Schema(description = "contrasena del usuario", example = "123456789")
    private String password;
}
