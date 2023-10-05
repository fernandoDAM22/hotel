package com.hotel.hotel.controller;

import com.hotel.hotel.components.ErrorUtils;
import com.hotel.hotel.components.JWTUtil;
import com.hotel.hotel.dto.InsertReservationDTO;
import com.hotel.hotel.dto.ReservationDTO;
import com.hotel.hotel.other.Status;
import com.hotel.hotel.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/reservations")
@RequiredArgsConstructor
@Tag(name = "Reservations", description = "Operaciones relacionadas con las reservas")
public class ReservationController {
    private final JWTUtil jwtUtil;
    private final ReservationService service;
    private final ErrorUtils errorUtils;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Reservar", description = "Reserva una habitacion")
    public ResponseEntity<ReservationDTO> reserve(@Parameter(description = "token de autenticacion del usuario") @RequestHeader("token") String token,
                                                  @Parameter(description = "dto con los datos de la reserva a insertar") @Valid @RequestBody InsertReservationDTO dto, BindingResult bindingResult) {
        errorUtils.handle(bindingResult);
        jwtUtil.checkUser(token);
        Long id = Long.parseLong(jwtUtil.getKey(token));
        return ResponseEntity.status(HttpStatus.CREATED).body(service.reserve(dto, id));
    }

    /**
     * Este metodo permite obtener todas las reservas de la base de datos
     *
     * @param token es el token de autenticacion del usuario
     * @return una lista de dtos con los datos de todas las reservas de la base de datos
     */
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Obtener reservas", description = "Obtiene todas las reservas de la base de datos")
    public ResponseEntity<List<ReservationDTO>> getAllReserves(@Parameter(description = "token de autenticacion del usuario") @RequestHeader("token") String token) {
        jwtUtil.validarAdmin(token);
        return ResponseEntity.ok(service.getAllReserves());
    }

    /**
     * Este metodo permite obtener los datos de una reserva por su id
     *
     * @param token es el token de auntenticacion del usuario
     * @param id    es el id de la reserva que queremos obtener
     * @return un dto con los datos de la reserva indicada
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Obtener reserva", description = "Obtiene una reserva de la base de datos a traves de su id")
    public ResponseEntity<ReservationDTO> getReservation(@Parameter(description = "token de autenticacion del usuario") @RequestHeader("token") String token,
                                                         @Parameter(description = "id de la reserva") @PathVariable Long id) {
        jwtUtil.checkUser(token);
        Long userId = Long.parseLong(jwtUtil.getKey(token));
        return ResponseEntity.ok(service.findById(id, userId));
    }

    /**
     * Este metodo permite obtener todas las consultas cuyo status se encuentre en la lista indicada
     *
     * @param token  es el token de autenticacion del usuario
     * @param status es la lista de estados
     * @return una lista de dtos con los datos de las reservas cuyo estado se encuentra en la lista indicada
     */
    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Obtener reservas por estado", description = "Obtiene todas las reservas de la base de datos cuyo estado se encuentre entre los indicados")
    public ResponseEntity<List<ReservationDTO>> findByStatus(
            @Parameter(description = "token de autenticacion del usuario") @RequestHeader("token") String token,
            @Parameter(description = "lista con los estados") @RequestParam("status") List<Status> status) {
        jwtUtil.validarAdmin(token);
        return ResponseEntity.ok(service.findByStatus(status));
    }

    /**
     * Este metodo permite obtener todas las reservas de una habitacion
     *
     * @param token es el token de autenticacion del usuario
     * @param id    es el id de la habitacion de la que queremos obtener las reservas
     * @return una lista de dtos con los datos de todas las reservas de la habitacion indicada
     */
    @GetMapping("/room/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Obtener reservas por habitacion", description = "Obtiene todas las reservas de la base de datos de una habitacion a traves de su id")
    public ResponseEntity<List<ReservationDTO>> getReservationsByRoom(@Parameter(description = "token de autenticacion del usuario") @RequestHeader("token") String token,
                                                                      @Parameter(description = "id de la habitacion") @PathVariable Long id) {
        jwtUtil.validarAdmin(token);
        return ResponseEntity.ok(service.getReservesByRoom(id));
    }

    /**
     * Este metodo permite obtener todas las reservas de un usuario
     *
     * @param token es el token de autenticacion del usuario
     * @return una lista de dtos con los datos de todas las reservas del usuario indicado
     */
    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Obtener reservas por usuario", description = "Obtiene todas las reservas de la base de datos asociadas a un usuario")
    public ResponseEntity<List<ReservationDTO>> getReservationsByUser(@Parameter(description = "token de autenticacion del usuario") @RequestHeader("token") String token) {
        jwtUtil.checkUser(token);
        Long id = Long.parseLong(jwtUtil.getKey(token));
        return ResponseEntity.ok(service.getReservationsByUser(id));
    }

    /**
     * Este metodo permite confirmar una reserva
     *
     * @param token es el token de autenticacion del usuario
     * @param id    es el id de la reserva que queremos confirmar
     * @return un dto con los datos de la reserva confirmada
     */
    @GetMapping("/confirm/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Confirmar", description = "Permite confirmar una reserva")
    public ResponseEntity<ReservationDTO> confirm(@Parameter(description = "token de autenticacion del usuario") @RequestHeader("token") String token,
                                                  @Parameter(description = "id de la reserva") @PathVariable Long id) {
        jwtUtil.checkUser(token);
        Long userId = Long.parseLong(jwtUtil.getKey(token));
        return ResponseEntity.ok(service.confirm(id, userId));
    }

    /**
     * Este metodo permite cancelar una reserva
     *
     * @param token es el token de autenticacion del usuario
     * @param id    es el id de la reserva que queremos cancelar
     * @return una dto con los datos de la reserva cancelada
     */
    @GetMapping("/cancel/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Cancelar", description = "Permite cancelar una reserva")
    public ResponseEntity<ReservationDTO> cancel(@Parameter(description = "token de autenticacion del usuario") @RequestHeader("token") String token,
                                                 @Parameter(description = "id de la reserva") @PathVariable Long id) {
        jwtUtil.checkUser(token);
        Long userId = Long.parseLong(jwtUtil.getKey(token));
        return ResponseEntity.ok(service.cancel(id, userId));
    }

    /**
     * Este metodo permite comprobar si una habitacion esta disponible entre dos dias indicados
     *
     * @param token es el token de autenticacion del usuario
     * @param entry es la fecha de entrada
     * @param exit  es la fecha de salida
     * @param id    es el id de la habitacion que queremos comprobar
     * @return un mensaje indicando si la habitacion esta disponible o no
     */
    @GetMapping("/available/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Comprobar disponibilidad", description = "Permite comprobar si una habitacion esta disponible entre dos dias indicados")
    public ResponseEntity<String> isAvailable(@Parameter(description = "token de autenticacion del usuario") @RequestHeader("token") String token,
                                              @Parameter(description = "Fecha de entrada") @RequestParam("entry") Date entry,
                                              @Parameter(description = "Fecha de salida") @RequestParam("exit") Date exit,
                                              @Parameter(description = "id de la habitacion") @PathVariable Long id) {
        jwtUtil.checkUser(token);
        return ResponseEntity.ok(service.getAvailability(id, entry, exit));
    }

}
