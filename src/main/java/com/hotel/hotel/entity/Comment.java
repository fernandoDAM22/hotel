package com.hotel.hotel.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Data
@EntityListeners(AuditingEntityListener.class) // Habilita la auditoría
@Schema(description = "Entidad que representa un comentario")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Id del comentario", example = "1")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @Schema(description = "Usuario asociado al comentario")
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    @Schema(description = "Habitacion asociada al comentario")
    private Room room;

    @Min(value = 1, message = "error.comment.min")
    @Max(value = 5, message = "error.comment.max")
    @Column(name = "qualification")
    @Schema(description = "Calificacion de la habitacion", example = "4")
    private Integer qualification;

    @NotEmpty(message = "error.comment.comment")
    @Column(name = "comment", length = 500)
    @Schema(description = "Comentario", example = "Muy bonita la habitacion")
    private String comment;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    @Schema(description = "Fecha del comentario", example = "22/10/2023 16:20:52")
    private LocalDateTime date;

}
