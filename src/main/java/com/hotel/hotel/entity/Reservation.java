package com.hotel.hotel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hotel.hotel.other.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "reservation")
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class) // Habilita la auditoría
@Schema(description = "Entidad que representa una reserva")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Id de la reserva", example = "1")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull
    @Schema(description = "Usuario asociado a la reserva")
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    @NotNull
    @Schema(description = "habitacion asociada a la reserva")
    private Room room;

    @Column(name = "entry_date")
    @NotNull(message = "error.reservation.entry")
    @Schema(description = "fecha de entrada", example = "10/10/2023")
    private Date entryDate;

    @Column(name = "exit_date")
    @NotNull(message = "error.reservation.exit")
    @Schema(description = "fecha de salida", example = "15/10/2023")
    private Date exitDate;

    @Column(name = "creation_date")
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Schema(description = "Fecha de creacion de la reserva", example = "05/10/2023 18:10:28")
    private LocalDateTime creationDate;

    @Column(name = "total_price")
    @Schema(description = "Precio total de la reserva", example = "100")
    private BigDecimal totalPrice;

    @Column(name = "status", length = 20, nullable = false)
    @Schema(description = "Estado de la reserva", example = "PENDIENTE")
    @Enumerated(EnumType.STRING)
    private Status status;
}
