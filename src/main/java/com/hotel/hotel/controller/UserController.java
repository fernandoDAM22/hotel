package com.hotel.hotel.controller;


import com.hotel.hotel.components.ErrorUtils;
import com.hotel.hotel.components.JWTUtil;
import com.hotel.hotel.dto.InsertUserDTO;
import com.hotel.hotel.dto.LoginUserDTO;
import com.hotel.hotel.dto.UserDTO;
import com.hotel.hotel.entity.User;
import com.hotel.hotel.service.UserService;
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
@RequestMapping("api/users")
@Tag(name = "User", description = "Operaciones relacionadas con los usuarios")
public class UserController {
    /**
     * Instancia del servicio
      */
    private final UserService service;
    /**
     * Instancia del componente que nos permite resolver los errores de validacion
     */
    private final ErrorUtils errorUtils;
    /**
     * Instancia del componente que nos permite acceder a la logica de JWT
      */
    private final JWTUtil jwtUtil;

    /**
     * Este metodo permite insertar un usuario en la base de datos
     *
     * @param dto           es el dto que contiene los datos del usuario que queremos insertar
     * @param bindingResult es el objeto que contiene los errores de validacion
     * @return un ResponseEntity con el DTO insertado y un status code 201
     */
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Insertar", description = "Inserta un usuario en la base de datos")
    public ResponseEntity<UserDTO> insert(@Parameter(description = "dto con los datos del usuario a insertar") @Valid @RequestBody InsertUserDTO dto, BindingResult bindingResult) {
        errorUtils.handle(bindingResult);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.insert(dto));

    }

    /**
     * Este metodo permite que un usuario se loguee en el sistema
     *
     * @param dto es el dto con las credenciales del usuario que se quiere loguear
     * @return el token de autenticacion del usuario que se loguea
     */
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Login", description = "Permite a un usuario loguearse en el sistema")
    public ResponseEntity<String> login(@Parameter(description = "dto con los datos del usuario que se quiere loguear") @RequestBody LoginUserDTO dto) {
        User user = service.login(dto);
        return ResponseEntity.ok(jwtUtil.create(String.valueOf(user.getId()), user.getEmail()));
    }

    /**
     * Este metodo permite obtener todos los usuarios de la base de datos en formato DTO
     *
     * @param token es el token de autenticacion del usuario
     * @return una lista con los DTOS con los datos de todos los usuarios
     */
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Obtener usuarios", description = "Permite obtener todos los usuarios de la base de datos")
    public ResponseEntity<List<UserDTO>> getAllUsers(@Parameter(description = "token de autenticacion del usuario") @RequestHeader("token") String token) {
        jwtUtil.validarAdmin(token);
        return ResponseEntity.ok(service.getAllUsers());
    }

    /**
     * Este metodo permite obtener un usuario de la base de datos en formato DTO
     * a traves de su id
     *
     * @param token es el token de autenticacion del usuario
     * @param id    es el id del usuario que queremos obtener
     * @return un DTO con los datos del usuario indicado
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Obtener usuario", description = "Obtiene un usuario de la base de datos por su id")
    public ResponseEntity<UserDTO> getUserById(@Parameter(description = "token de autenticacion del usuario") @RequestHeader("token") String token,
                                               @Parameter(description = "id del usuario") @PathVariable Long id) {
        jwtUtil.validarAdmin(token);
        return ResponseEntity.ok(service.findByIdAndConvert(id));
    }

    /**
     * Este metodo permite eliminar un usuario de la base de datos
     * @param token es el token de autenticacion del usuario
     * @param id es el id del usuario que queremos borrar
     * @return un ResponseEntity con un status code 204
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Borrar", description = "Permite borrar un usuario de la base de datos")
    public ResponseEntity<UserDTO> deleteUser(@Parameter(description = "token de autenticacion del usuario") @RequestHeader("token") String token,
                                              @Parameter(description = "id del usuario") @PathVariable Long id) {
        jwtUtil.validarAdmin(token);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Este metodo permite modificar el nombre y email de un usuario
     * @param token es el token de autenticacion del usuario
     * @param userDTO es el dto con los datos que se quieren modificar
     * @param bindingResult es el objeto que contiene los errores de validacion
     * @return el dto con los nuevos datos persistidos en la base de datos
     */
    @PutMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Actualizar", description = "Permite actualizar un usuario de la base de datos")
    public ResponseEntity<UserDTO> updateUser(@Parameter(description = "token de autenticacion del usuario") @RequestHeader("token") String token,
                                              @Parameter(description = "dto con los datos del usuario a insertar") @Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        jwtUtil.checkUser(token);
        errorUtils.handle(bindingResult);
        Long id = Long.parseLong(jwtUtil.getKey(token));
        return ResponseEntity.ok(service.update(userDTO,id));
    }

    /**
     * Este metodo permite actualizar la contrasena de un usuario
     * @param token es el token de autenticacion del usuario que quiere modificar su contrasena
     * @param password es la nueva contrasena del usuario
     * @return un mensaje indicando que se ha modificiado la contrasena correctamente
     */
    @PatchMapping("/update/password")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Actualizar contrasena", description = "Permite a un usuario actualizar su contrasena")
    public ResponseEntity<String> updatePassword(@Parameter(description = "token de autenticacion del usuario") @RequestHeader("token") String token,
                                                 @Parameter(description = "contrasena nueva del usuario") @RequestBody String password){
        jwtUtil.checkUser(token);
        Long id = Long.parseLong(jwtUtil.getKey(token));
        return ResponseEntity.ok(service.updatePassword(password,id));
    }



}
