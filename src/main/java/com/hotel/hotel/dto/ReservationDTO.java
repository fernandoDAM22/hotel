package com.hotel.hotel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hotel.hotel.other.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
@Schema(description = "dto para enviar los datos de una reserva")
public class ReservationDTO {
    @Schema(description = "nombre del usuario", example = "fernando")
    private String user;

    @Schema(description = "id de la habitacion", example = "1")
    private int room;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Schema(description = "Fecha de entrada", example = "10/10/2023")
    private Date entryDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Schema(description = "Fecha de salida", example = "15/10/2023")
    private Date exitDate;

    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime creationDate;

    @Schema(description = "Precio total de la reserva", example = "40")
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Estado de la reserva", example = "Pendiente")
    private Status status;
}
