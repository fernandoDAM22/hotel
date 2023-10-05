package com.hotel.hotel.controller;

import com.hotel.hotel.components.JWTUtil;
import com.hotel.hotel.dto.InsertRoomDTO;
import com.hotel.hotel.dto.RoomDTO;
import com.hotel.hotel.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/rooms")
@RequiredArgsConstructor
@Tag(name = "Room", description = "Operaciones relacionadas con las habitaciones")
public class RoomController {
    /**
     * Ruta donde se suben las imagenes de las habitaciones
     */
    @Value("${images.path}")
    private String path;
    /**
     * Instancia de JWTUtil que nos permite acceder a la logica de JWT
     */
    private final JWTUtil jwtUtil;
    /**
     * Instancia del servicio
     */
    private final RoomService service;

    /**
     * Este metodo permite dar de alta una habitacion
     *
     * @param token es el token de autenticacion del usuario
     * @param file  es la imagen que se le va a asignar a la habitacion
     * @param dto   es el dto con los datos que se van a insertar
     * @return un dto con los datos de la habitacion insertada
     */
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Insertar", description = "Insertar una habitacion en la base de datos")
    public ResponseEntity<RoomDTO> insert(@Parameter(description = "token de autenticacion del usuario") @RequestHeader("token") String token,
                                          @Parameter(description = "imagen de la habitacion") @RequestPart("file") MultipartFile file,
                                          @Parameter(description = "dto con los datos de la habitacion") @RequestPart("room") InsertRoomDTO dto) {
        jwtUtil.validarAdmin(token);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.insert(dto, file, path));
    }

    /**
     * Este metodo permite obtener todas las habitaciones de la base de datos
     *
     * @param token es el token de autenticacion del usuario
     * @return una lista de dtos con los datos de todas las habitaciones
     */
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Obtener habitaciones", description = "Obtiene todas las habitaciones de la base de datos")
    public ResponseEntity<List<RoomDTO>> getAllRooms(@Parameter(description = "token de autenticacion del usuario") @RequestHeader("token") String token) {
        jwtUtil.checkUser(token);
        return ResponseEntity.ok(service.getAllRooms());
    }

    /**
     * Este metodo permite obtener una habitacion por su id
     *
     * @param token es el token de autenticacion del usuario
     * @param id    es el id de la habitacion que queremos obtener
     * @return un dto los los datos de la habitacion con el id indicado
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Obtener habitacion", description = "Obtiene una habitacion por su id")
    public ResponseEntity<RoomDTO> getRoom(@Parameter(description = "token de autenticacion del usuario") @RequestHeader("token") String token,
                                           @Parameter(description = "id de la habitacion") @PathVariable Long id) {
        jwtUtil.checkUser(token);
        return ResponseEntity.ok(service.findByIdAndConvert(id));
    }

    /**
     * Este metodo permite obtener todas las habitaciones de la base de datos cuyo tipo se encuentre en la lista determinada
     *
     * @param token es el token de autenticacion del usuario
     * @param types es la lista de tipos
     * @return una lista de dtos con los datos de las habitaciones cuyo tipo se encuentre en la lista determinada
     */
    @GetMapping("/type")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Obtener habitaciones por tipo", description = "Obtiene todas las habitaciones cuyo tipo se encuentre entre los indicados")
    public ResponseEntity<List<RoomDTO>> getRoomsByTypeIn(@Parameter(description = "token de autenticacion del usuario") @RequestHeader("token") String token,
                                                          @Parameter(description = "lista con los tipos de las habitaciones") @RequestParam("types") List<String> types) {
        jwtUtil.checkUser(token);
        return ResponseEntity.ok(service.findByTypeIn(types));
    }

    /**
     * Este metodo permite obtener todas las habitaciones de la base de datos con una capacidad determinada
     *
     * @param token    es el token de autenticacion del usuario
     * @param capacity es la capacidad de las habitaciones que queremos obtener
     * @return una lista de dtos con los datos de las habitaciones con la capacidad indicada
     */
    @GetMapping("/capacity")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Obtener habitaciones por capacidad", description = "Obtiene utodas las habitaciones cuya capacidad sea la indicada")
    public ResponseEntity<List<RoomDTO>> getRoomsByCapacity(@Parameter(description = "token de autenticacion del usuario") @RequestHeader("token") String token,
                                                            @Parameter(description = "capacidad de la habitacion") @RequestParam("capacity") Long capacity) {
        jwtUtil.checkUser(token);
        return ResponseEntity.ok(service.findByCapacity(capacity));
    }

    /**
     * Este metodo permite obtener todas las habitaciones de la base de datos cuyo precio se encuentre en un rango
     * determinado
     *
     * @param token es el token de autenticacion del usuario
     * @param min   es el precio minimo del rango
     * @param max   es el precio maximo del rango
     * @return una lista de dtos con los datos de las habitaciones cuyo precio se encuentre entre el rango indicado
     */
    @GetMapping("/price")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Obtener habitaciones por precio", description = "Obtiene todas las habitaciones cuyo precio se encuentre entre el rango indicado")
    public ResponseEntity<List<RoomDTO>> getRoomsByPrice( @Parameter(description = "token de autenticacion del usuario")@RequestHeader("token") String token,
                                                          @Parameter(description = "precio minimo de la habitacion") @RequestParam("min") int min,
                                                          @Parameter(description = "precio maximo de la habitacion") @RequestParam("max") int max) {
        jwtUtil.checkUser(token);
        return ResponseEntity.ok(service.findByPrice(min, max));
    }


    /**
     * Este metodo permite modificar una habitacion a excepcion de su imagen
     *
     * @param token es el token de autenticacion del usuario
     * @param dto   es el dto con los nuevos datos de la habitacion
     * @return un dto con los datos de la habitacion modificada
     */
    @PutMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Actualizar", description = "Permite actualizar los datos de una habitacion")
    public ResponseEntity<RoomDTO> update(@Parameter(description = "token de autenticacion del usuario") @RequestHeader("token") String token,
                                          @Parameter(description = "dto con los datos a actualizar") @RequestBody RoomDTO dto) {
        jwtUtil.validarAdmin(token);
        return ResponseEntity.ok(service.update(dto));
    }

    /**
     * Este metodo permite modificar la imagen de una habitacion
     *
     * @param token es el token de autenticacion del usuario
     * @param file  es la nueva imagen de la habitacion
     * @param id    es el id de la habitacion a la que le vamos a modificar la imagen
     * @return un dto con los datos de la habitacion modificada
     */
    @PatchMapping("/image/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Actualizar imagen", description = "Permite actualizar la imagen de una habitacion")
    public ResponseEntity<RoomDTO> updateImage(@Parameter(description = "token de autenticacion del usuario") @RequestHeader("token") String token,
                                               @Parameter(description = "imagen para asignar a la habitacion") @RequestPart("file") MultipartFile file,
                                               @Parameter(description = "id de la habitacion") @PathVariable Long id) {
        jwtUtil.validarAdmin(token);
        return ResponseEntity.ok(service.updateImage(id, file, path));
    }
}
