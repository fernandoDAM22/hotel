package com.hotel.hotel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@Schema(description = "dto para enviar los datos de un comentario")
public class CommentDTO {
    @Schema(description = "Nombre del usuario", example = "fernando")
    private String user;
    @Schema(description = "Id de la habitacion", example = "120")
    private Long room;
    @Schema(description = "Calificacion de la habitacion", example = "4")
    private Integer qualification;
    @Schema(description = "Comentario", example = "Muy Bonita la habitacion")
    private String comment;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/mm/yyyy hh:mm:ss")
    @Schema(description = "Fecha de creacion del comentario", example = "20/05/2023 18:12:20")
    private LocalDateTime date;
}
