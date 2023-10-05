package com.hotel.hotel.controller;

import com.hotel.hotel.components.ErrorUtils;
import com.hotel.hotel.components.JWTUtil;
import com.hotel.hotel.dto.CommentDTO;
import com.hotel.hotel.dto.InsertCommentDTO;
import com.hotel.hotel.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/comments")
@Tag(name = "Comments", description = "Operaciones relacionadas con los comentarios")
public class CommentController {
    /**
     * Intancia del servicio
     */
    private final CommentService service;
    /**
     * Intancia de JWTUtil para poder acceder a la logica de JWT
     */
    private final JWTUtil jwtUtil;
    /**
     * Instancia del componente para manejar los errores
     */
    private final ErrorUtils errorUtils;

    /**
     * Este metodo permite insertar un comentario
     *
     * @param token es el token de autenticacion del usuario
     * @param dto es el dto con los datos del comentario
     * @param bindingResult es el objeto que nos permite manejar los errores
     * @return un dto con los datos del comentario insertado
     */
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Insertar un comentario", description = "Inserta un comentario en la base de datos")
    public ResponseEntity<CommentDTO> insert(
            @Parameter(description = "token de autenticacion del usuario") @RequestHeader("token") String token,
            @Parameter(description = "dto con los datos del comentario a insertar") @Valid @RequestBody InsertCommentDTO dto, BindingResult bindingResult) {
        errorUtils.handle(bindingResult);
        jwtUtil.checkUser(token);
        Long id = Long.parseLong(jwtUtil.getKey(token));
        return ResponseEntity.ok(service.insert(dto, id));
    }

    /**
     * Este metodo permite obtener una lista con todos los comentarios de la base de datos
     * @param token es el token de autenticacion del usuario
     * @return una lista con todos los comentarios de la base de datos
     */
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Obtener comentarios", description = "Obtiene todos los comentarios de la base de datos")
    public ResponseEntity<List<CommentDTO>> getAllComments(@Parameter(description = "token de autenticacion del usuario") @RequestHeader("token") String token){
        jwtUtil.validarAdmin(token);
        return ResponseEntity.ok(service.getAllComments());
    }

    /**
     * Este metodo permite obtener un comentario por su id
     * @param token es el token de autenticacion del usuario
     * @param id es el id del comentario que se quiere obtener
     * @return un dto con los datos del comentario
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Obtener comentario", description = "Obtiene un comentario de la base de datos por su id")
    public ResponseEntity<CommentDTO> getComment(@Parameter(description = "token de autenticacion del usuario") @RequestHeader("token") String token,
                                                 @Parameter(description = "Id del comentario") @PathVariable Long id){
        jwtUtil.checkUser(token);
        Long userId = Long.parseLong(jwtUtil.getKey(token));
        return ResponseEntity.ok(service.findById(id,userId));
    }

    /**
     * Este metodo permite obtener todos los comentarios de una habitacion
     * @param token es el token de autenticacion del usuario
     * @param id es el id de la habitacion
     * @return una lista de dtos con los datos de todos los comentarios de esa habitacion
     */
    @GetMapping("/room/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Obtener comentarios por habitacion", description = "Obtiene todos los comentarios de la base de datos asociados a una habitacion")
    public ResponseEntity<List<CommentDTO>> getCommentByRoom(@Parameter(description = "token de autenticacion del usuario") @RequestHeader("token") String token,
                                                             @Parameter(description = "Id de la habitacion") @PathVariable Long id){
        jwtUtil.validarAdmin(token);
        return ResponseEntity.ok(service.findByRoom(id));
    }

    /**
     * Este metodo permite obtener todos los comentarios de un usuario
     * @param token es el token de autenticacion del usuario
     * @return una lista de dtos con los datos de todos los comentarios del usaurio
     */
    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Obtener comentarios por usuario", description = "Obtiene todos los comentarios de la base de datos asociados a un usuario")
    public ResponseEntity<List<CommentDTO>> getCommentByUser(@Parameter(description = "token de autenticacion del usuario") @RequestHeader("token") String token){
        jwtUtil.checkUser(token);
        Long id = Long.parseLong(jwtUtil.getKey(token));
        return ResponseEntity.ok(service.findByUser(id));
    }

    /**
     * Este metodo permite borrar un comentario
     * @param token es el token de autentiacion del usuario
     * @param id es el id del comentario que queremos borrar
     * @return un ResponseEntity No Content
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Borrar comentario", description = "Borra un comentario de la base de datos")
    public ResponseEntity<?> deleteComment(@Parameter(description = "token de autenticacion del usuario") @RequestHeader("token") String token,
                                           @Parameter(description = "id del comentario") @PathVariable Long id){
        jwtUtil.checkUser(token);
        Long userId = Long.parseLong(jwtUtil.getKey(token));
        service.delete(id,userId);
        return ResponseEntity.noContent().build();
    }
}
