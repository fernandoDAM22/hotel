package com.hotel.hotel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Schema(description = "dto con los datos a enviar de una habitacion")
public class RoomDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Id de la habitacion", example = "1")
    private Long id;

    @NotEmpty(message = "error.room.tipo")
    @Size(max = 50,message = "error.room_longitud_tipo")
    @Column(name = "type")
    @Schema(description = "Tipo de la habitacion", example = "Individual")
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
    @Schema(description = "Imagen de la habitacion", example = "C:/images/room.jpg")
    private String image;
}
