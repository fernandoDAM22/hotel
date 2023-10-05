package com.hotel.hotel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
@Schema(description = "dto con los datos para insertar una reserva")
public class InsertReservationDTO {
    @Schema(description = "id de la habitacion", example = "1")
    private Long roomId;

    @Column(name = "entry_date")
    @NotNull(message = "error.reservation.entry")
    @Future(message = "error.reservation.fechas")
    @Schema(description = "Fecha de entrada", example = "10/10/2023")
    private Date entryDate;

    @Column(name = "exit_date")
    @NotNull(message = "error.reservation.exit")
    @Future(message = "error.reservation.fechas")
    @Schema(description = "Fecha de salida", example = "15/10/2023")
    private Date exitDate;

    @Column(name = "creation_date")
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Schema(description = "Fecha de creacion", example = "05/10/2023 18:15:35")
    private LocalDateTime creationDate;

}
