package com.hotel.hotel.entity;


import com.hotel.hotel.other.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "user")
@Data
@Schema(description = "Entidad que representa un usuario")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "id del usuario", example = "1")
    private Long id;

    @NotEmpty(message = "error.user.nombre_vacio")
    @Size(max = 50, message = "error.user.nombre")
    @Column(name = "name")
    @Schema(description = "Nombre del usuario", example = "Fernando")
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
    @Schema(description = "contrasena cifrada del usuario", example = "HASHED_PASSWORD")
    private String password;

    @Column(name = "type", length = 10, nullable = true)
    @Enumerated(EnumType.STRING)
    @Schema(description = "Tipo del usuario", example = "ADMIN")
    private Role type;
}
