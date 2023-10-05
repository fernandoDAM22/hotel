package com.hotel.hotel.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "room")
@Schema(description = "Entidad que representa una habitacion")
@Data
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Id de la habitacion", example = "1")
    private Long id;

    @NotEmpty(message = "error.room.tipo")
    @Size(max = 50,message = "error.room_longitud_tipo")
    @Column(name = "type")
    @Schema(description = "tipo de la habitacion", example = "individual")
    private String type;

    @NotNull(message = "error.room_capacidad")
    @Column(name = "capacity")
    @Schema(description = "Capacidad de la habitacion", example = "1")
    private Integer capacity;

    @NotNull(message = "error.room.precio")
    @Column(name = "price")
    @Schema(description = "Precio de la habitacion", example = "40")
    private Long price;

    @NotEmpty(message = "error.room.descripcion")
    @Size(max = 500,message = "error.room.longitud_descripcion")
    @Column(name = "descripcion")
    @Schema(description = "Descripcion de la habitacion", example = "Habitacion con bonitas vistas")
    private String descripcion;

    @Size(max = 256)
    @Column(name = "image")
    @Schema(description = "Imagen de la habitacion", example = "C:/imagenes/room.jpg")
    private String image;
}
