package com.hotel.hotel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Schema(description = "Dto con los datos para insertar un comentario")
public class InsertCommentDTO {
    @Schema(description = "Id de la habitacion", example = "120")
    private Long roomId;

    @Min(value = 1, message = "error.comment.min")
    @Max(value = 5, message = "error.comment.max")
    @Column(name = "qualification")
    @Schema(description = "Calificacion de la habitacion", example = "4")
    private Integer qualification;

    @Schema(description = "Comentario", example = "Muy Bonita la habitacion")
    @NotEmpty(message = "error.comment.comment")
    @Column(name = "comment", length = 500)
    private String comment;
}
