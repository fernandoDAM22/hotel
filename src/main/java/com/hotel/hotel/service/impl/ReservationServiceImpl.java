package com.hotel.hotel.service.impl;

import com.hotel.hotel.components.DTOConverter;
import com.hotel.hotel.components.MessageComponent;
import com.hotel.hotel.dto.InsertReservationDTO;
import com.hotel.hotel.dto.ReservationDTO;
import com.hotel.hotel.dto.RoomDTO;
import com.hotel.hotel.entity.Reservation;
import com.hotel.hotel.entity.Room;
import com.hotel.hotel.entity.User;
import com.hotel.hotel.exception.RoomNotAvailableException;
import com.hotel.hotel.other.Role;
import com.hotel.hotel.other.Status;
import com.hotel.hotel.repository.ReservationRepository;
import com.hotel.hotel.service.ReservationService;
import com.hotel.hotel.service.RoomService;
import com.hotel.hotel.service.UserService;
import jakarta.validation.constraints.AssertTrue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    /**
     * Instancia del repositorio
     */
    private final ReservationRepository repository;
    /**
     * Instancia del servicio de usuarios
     */
    private final UserService userService;
    /**
     * Instancia del servicio de habitaciones
     */
    private final RoomService roomService;
    /**
     * Instancia del convertidor de dtos
     */
    private final DTOConverter converter;
    private final MessageComponent message;

    /**
     * Este metodo permite insertar una reserva
     *
     * @param dto    es el dto con los datos de la reserva
     * @param userId es el id del usuario que quiere realizar la reserva
     * @return un dto con los datos de la reserva creada
     */
    @Override
    public ReservationDTO reserve(InsertReservationDTO dto, Long userId) {
        if (!isAvailable(dto.getRoomId(), dto.getEntryDate(), dto.getExitDate())) {
            throw new RoomNotAvailableException("error.room.reservada");
        }
        if (!isValidDateRange(dto)) {
            throw new IllegalArgumentException("error.reservations.entrada_salida");
        }

        RoomDTO roomdto = roomService.findByIdAndConvert(dto.getRoomId());
        Room room = converter.convertToRoom(roomdto);
        Reservation reservation = converter.convertDTOToReservation(dto);
        reservation.setRoom(room);
        User user = userService.findById(userId);
        reservation.setUser(user);
        reservation.setTotalPrice(BigDecimal.valueOf(calculateDays(reservation.getEntryDate(), reservation.getExitDate()) * reservation.getRoom().getPrice()));
        return converter.convertReservationToDTO(repository.save(reservation));

    }

    /**
     * Este metodo permite comprobar si la fecha de entrada es anterior a
     * la fecha de salida
     * @param reservationDTO Es el objeto del cual queremos comprobar las fechas
     * @return true si la fecha de entrada es anterior a la de salida, false si no
     */
    @Override
    public boolean isValidDateRange(InsertReservationDTO reservationDTO) {
        if (reservationDTO.getEntryDate() == null || reservationDTO.getExitDate() == null) {
            return true; // No aplica si alguna fecha es nula
        }
        return reservationDTO.getEntryDate().before(reservationDTO.getExitDate());
    }

    /**
     * Este metodo permite comprobar si una habitacion esta disponible  los dias
     * seleccionados por la reserva
     *
     * @param roomId es el id de la habitacion
     * @param entry  es la fecha de entrada
     * @param exit   es la fecha de salida
     * @return true si esta disponible, false si no
     */
    @Override
    public boolean isAvailable(Long roomId, Date entry, Date exit) {
        int reservations = repository.isAvailable(roomId, entry, exit);
        return reservations == 0;
    }

    /**
     * Este metodo permite calcular los dias que pasan entre la fecha
     * de entrada y la fecha de salida
     *
     * @param entry es la fecha de entrada
     * @param exit  es la fecha de salida
     * @return los dias que van desde una fecha a otra
     */
    @Override
    public int calculateDays(Date entry, Date exit) {
        // Obtenemos la representación en milisegundos de las fechas
        long entryMillis = entry.getTime();
        long exitMillis = exit.getTime();
        // Calculamos la diferencia en milisegundos
        long differenceMillis = exitMillis - entryMillis;
        // Convertimos la diferencia a días (1 día = 24 horas * 60 minutos * 60 segundos * 1000 milisegundos)
        return (int) (differenceMillis / (24 * 60 * 60 * 1000));
    }

    /**
     * Este metodo permite obtener todas las reservas de la base de datos
     *
     * @return una lista de dtos con los datos de todas las reservas de la base de datos
     */
    @Override
    public List<ReservationDTO> getAllReserves() {
        return repository.findAll().stream()
                .map(converter::convertReservationToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Este metodo permite obtener una reserva por su id
     *
     * @param id     es el id de la reserva que queremos obtener
     * @param userId es el id del usuario que quiere obtener la reserva
     * @return un dto con los datos de la reserva indicada
     */
    @Override
    public ReservationDTO findById(Long id, Long userId) {
        Reservation reservation = repository.findById(id).orElseThrow(() -> new NoSuchElementException("error.reservation.id"));
        User user = userService.findById(userId);
        if (user.getType().equals(Role.ADMIN) || reservation.getUser().equals(user)) {
            return converter.convertReservationToDTO(reservation);
        }
        throw new NoSuchElementException("error.reservation.id");
    }

    /**
     * Este metodo permite obtener todas las reservas de una habitacion
     *
     * @param id es el id de la habitacion de la que queremos obtener las reservas
     * @return una lista de dtos con los datos de todas las reservas de la habitacion indicada
     */
    @Override
    public List<ReservationDTO> getReservesByRoom(Long id) {
        Room room = roomService.findById(id);
        return repository.findByRoomId(room.getId()).stream()
                .map(converter::convertReservationToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationDTO> getReservationsByUser(Long id) {
        User user = userService.findById(id);
        return repository.findByUserId(user.getId()).stream()
                .map(converter::convertReservationToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Este metodo permite obtener todas las consultas cuyo status se encuentre en la lista indicada
     * @param status es la lista de estados
     * @return una lista de dtos con los datos de las reservas cuyo estado se encuentra en la lista indicada
     */
    @Override
    public List<ReservationDTO> findByStatus(List<Status> status) {
        return repository.findByStatusIn(status).stream()
                .map(converter::convertReservationToDTO)
                .collect(Collectors.toList());
    }

    /**
     * este metodo permite confirmar una reserva
     * @param id es el id de la reserva que queremos confirmar
     * @param userId es el id del usuario que quiere confirmar la reserva
     * @return un dto con los datos de la reserva confirmada
     */
    @Override
    public ReservationDTO confirm(Long id, Long userId) {
        Reservation reservation = repository.findById(id).orElseThrow(() -> new NoSuchElementException("error.reservation.id"));
        User user = userService.findById(userId);
        if (reservation.getUser().equals(user) && reservation.getStatus().equals(Status.PENDIENTE)) {
            reservation.setStatus(Status.CONFIRMADA);
            return converter.convertReservationToDTO(repository.save(reservation));
        }
        throw new NoSuchElementException("error.reservation.confirm");
    }
    /**
     * Este metodo permite cancelar una reserva
     * @param id es el id de la reserva que queremos cancelar
     * @param userId es el id del usuario que quiere cancelar la reserva
     * @return una dto con los datos de la reserva cancelada
     */
    @Override
    public ReservationDTO cancel(Long id, Long userId) {
        Reservation reservation = repository.findById(id).orElseThrow(() -> new NoSuchElementException("error.reservation.id"));
        User user = userService.findById(userId);
        if (reservation.getUser().equals(user) && !reservation.getStatus().equals(Status.CANCELADA)) {
            reservation.setStatus(Status.CANCELADA);
            return converter.convertReservationToDTO(repository.save(reservation));
        }
        throw new NoSuchElementException("error.reservation.cancel");
    }

    /**
     * Este metodo permite comprobar si una habitacion esta disponible entre dos dias indicados
     * @param id es el id de la habitacion que queremos comprobar
     * @param entry es la fecha de entrada
     * @param exit es la fecha de salida
     * @return un mensaje indicando si la habitacion esta disponible o no
     */
    @Override
    public String getAvailability(Long id, Date entry, Date exit) {
        if (!isAvailable(id,entry,exit)){
            throw new RoomNotAvailableException("error.room.reservada");
        }
        return message.getMessage("mensaje.room.disponible");
    }

}
