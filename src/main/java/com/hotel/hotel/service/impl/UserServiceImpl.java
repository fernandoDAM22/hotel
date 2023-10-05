package com.hotel.hotel.service.impl;

import com.hotel.hotel.components.DTOConverter;
import com.hotel.hotel.components.MessageComponent;
import com.hotel.hotel.dto.InsertUserDTO;
import com.hotel.hotel.dto.LoginUserDTO;
import com.hotel.hotel.dto.UserDTO;
import com.hotel.hotel.entity.User;
import com.hotel.hotel.exception.BadLoginExcepcion;
import com.hotel.hotel.exception.DuplicateUserException;
import com.hotel.hotel.exception.ForbiddenException;
import com.hotel.hotel.other.Role;
import com.hotel.hotel.repository.UserRepository;
import com.hotel.hotel.service.UserService;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.IllformedLocaleException;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    /**
     * instancia del repositorio
     */
    private final UserRepository repository;
    /**
     * Instancia del convertidor de dtos
     */
    private final DTOConverter converter;
    /**
     * Instancia del componetne que nos permite acceder al archivo de mensajes
     */
    private final MessageComponent message;

    /**
     * Este metodo permite insertar un usaurio en la base de datos
     *
     * @param dto es el dto con los datos del usuario que vamos a insertar
     * @return un DTO con los datos del usuario insertado
     */
    @Override
    public UserDTO insert(InsertUserDTO dto) {
        User user = converter.convertToUSer(dto);
        user.setType(Role.USER);
        if (repository.findByName(user.getName()).isPresent()) {
            throw new DuplicateUserException("error.usuario.nombre_repetido");
        }
        if (repository.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateUserException("error.usuario.email_repetido");
        }
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1, 1024, 1, user.getPassword().toCharArray());
        user.setPassword(hash);
        User dbUser = repository.save(user);
        return converter.convertToUserDto(dbUser);
    }

    /**
     * Este metodo permite comprobar las credenciales de un usuarios y devolver su
     * token de autenticacion si es son correctas
     * @param dto es el dto con las credenciales del usaurio que se quiere loguear
     * @return el token de autenticacion del usuario logueado
     */
    @Override
    public User login(LoginUserDTO dto) {
        User user = converter.convertDTOLoginToUser(dto);
        User dbUser = repository.findByEmail(user.getEmail()).orElseThrow(() -> new BadLoginExcepcion("error.usuario.login_email"));
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if (!argon2.verify(dbUser.getPassword(), user.getPassword())) {
            throw new BadLoginExcepcion("error.usuario.login_password");
        }
        return dbUser;
    }

    /**
     * Este metodo permite obtener un usuario por su id
     * @param id es el id del usuario que queremos obtener
     * @return el usuario con el id especificado
     */
    @Override
    public User findById(long id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchElementException("error.usuario.id"));
    }

    /**
     * Este metodo permite obtener todos los usuarios de la base de datos en DTOS
      * @return una lista de DTOS con los datos de los usuarios
     */
    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = repository.findAll();
        return users.stream()
                .map(converter::convertToUserDto)
                .collect(Collectors.toList());

    }

    /**
     * Este metodo permite obtener un usuario por su id y convertirlo a DTO
     * @param id el id del usuario que queremos obtener
     * @return un DTO con los datos del usuario con el id especificado
     */
    @Override
    public UserDTO findByIdAndConvert(Long id) {
        User user = repository.findById(id).orElseThrow(() -> new NoSuchElementException("error.usuario.id"));
        return converter.convertToUserDto(user);
    }

    /**
     * Este metodo permite eliminar un usuario por su id
     * @param id es el id del usuario que queremos eliminar
     */
    @Override
    public void delete(Long id) {
        User user = repository.findById(id).orElseThrow(() -> new NoSuchElementException("error.usuario.id"));
        repository.delete(user);
    }

    /**
     * Este metodo permite modificar el nombre y email de un usuario
     * @param userDTO es el dto con los datos que queremos modificar
     * @param id es el id del usuario que intenta realizar la modificacion
     * @return un dto con los nuevos datos persistidos en la base de datos
     */
    @Override
    public UserDTO update(UserDTO userDTO, Long id) {
        User dbUser = repository.findById(userDTO.getId()).orElseThrow(() -> new NoSuchElementException("error.usuario.id"));
        //se comprueba que el usuario que intenta realizar la modificacion solo se pueda modificar a el mismo
        if(!dbUser.getId().equals(id)){
            throw  new ForbiddenException("error.usuario.id");
        }
        //se comprueba que no exista ya un usuario en ese nombre
        if (repository.findByName(userDTO.getName()).isPresent() && !userDTO.getName().equals(dbUser.getName())) {
            throw new DuplicateUserException("error.usuario.nombre_repetido");
        }
        //se comprueba que no exista ya un usuario en ese email
        if (repository.findByEmail(userDTO.getEmail()).isPresent() && !userDTO.getEmail().equals(dbUser.getEmail())) {
            throw new DuplicateUserException("error.usuario.email_repetido");
        }
        dbUser.setName(userDTO.getName());
        dbUser.setEmail(userDTO.getEmail());
        return converter.convertToUserDto(repository.save(dbUser));
    }

    /**
     * Este metodo permite actualizar la contrasena de un usuario
     * @param password es la nueva contrasena del usuario
     * @param id es el id del usuario al que se le va a modificar la contrasena
     * @return un mensaje indicando que se ha modificado la contrasena correctamente
     */
    @Override
    public String updatePassword(String password, Long id) {
        if(password.length() < 8){
            throw new IllegalArgumentException("error.user.password");
        }
        User user = repository.findById(id).orElseThrow(() -> new NoSuchElementException("error.usuario.id"));
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1, 1024, 1, password);
        user.setPassword(hash);
        repository.save(user);
        return message.getMessage("mensaje.usuario.password");
    }
}
