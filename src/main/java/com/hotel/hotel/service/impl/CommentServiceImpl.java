package com.hotel.hotel.service.impl;

import com.hotel.hotel.components.DTOConverter;
import com.hotel.hotel.dto.CommentDTO;
import com.hotel.hotel.entity.Comment;
import com.hotel.hotel.dto.InsertCommentDTO;
import com.hotel.hotel.entity.Room;
import com.hotel.hotel.entity.User;
import com.hotel.hotel.other.Role;
import com.hotel.hotel.repository.CommentRepository;
import com.hotel.hotel.service.CommentService;
import com.hotel.hotel.service.RoomService;
import com.hotel.hotel.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    /**
     * Instancia del convertir de dtos
     */
    private DTOConverter converter;
    /**
     * Instanciad el servicio de usuarios
     */
    private UserService userService;
    /**
     * Instancia del servicio de habitaciones
     */
    private RoomService roomService;
    /**
     * Intancia del servicio de repositorios
     */
    private CommentRepository repository;

    /**
     * Este metodo permite guardar un comentario en la base de datos
     *
     * @param dto es el dto con los datos del comenario
     * @param id  es el id del usuario al cual pertenece el comentario
     * @return un dto con los datos del comentario guardado
     */
    @Override
    public CommentDTO insert(InsertCommentDTO dto, Long id) {
        Comment comment = converter.convertDTOToComment(dto);
        comment.setUser(userService.findById(id));
        comment.setRoom(roomService.findById(dto.getRoomId()));
        return converter.convertCommentToDTO(repository.save(comment));
    }

    /**
     * Este metodo permite obtener todos los comentarios de la base de datos
     *
     * @return una lista con todos los comentarios de la base de datos
     */
    @Override
    public List<CommentDTO> getAllComments() {
        return repository.findAll().stream()
                .map(converter::convertCommentToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Este metodo permite obtener un comentario por su id
     *
     * @param id es el id del comentario que queremos obtener
     * @return un dto con los datos del comentario
     */
    @Override
    public CommentDTO findById(Long id, Long userId) {
        Comment comment = repository.findById(id).orElseThrow(() -> new NoSuchElementException("error.comment.id"));
        User user = userService.findById(userId);
        if (user.getType().equals(Role.ADMIN) || comment.getUser().equals(user)) {
            return converter.convertCommentToDTO(comment);
        }
        throw new NoSuchElementException("error.comment.id");
    }

    /**
     * Este metodo permite obtener todos los comentarios de una habitacion
     * @param id es el id de la habitacion
     * @return una lista de dtos con los datos de todos los comentarios de la habitacion
     */
    @Override
    public List<CommentDTO> findByRoom(Long id) {
        Room room = roomService.findById(id);
        return repository.findByRoomId(room.getId()).stream()
                .map(converter::convertCommentToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Este metodo permite obtener todos los comentarios de un usuario
     * @param id es el id del usuario del que queremos obtener los comentarios
     * @return una lista de dtos con los datos de todos los comentarios de ese usuario
     */
    @Override
    public List<CommentDTO> findByUser(Long id) {
        return repository.findByUserId(id).stream()
                .map(converter::convertCommentToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Este metodo permite borrar un comentario por su id
     * @param id es el id del usuario que queremos borrar
     * @param userId es el id del usuario que intenta borrar el comentario
     */
    @Override
    public void delete(Long id, Long userId) {
        Comment comment = repository.findById(id).orElseThrow(() -> new NoSuchElementException("error.comment.id"));
        User user = userService.findById(userId);
        if(user.getType().equals(Role.ADMIN) || comment.getUser().equals(user)){
            repository.delete(comment);
            return;
        }
        throw new NoSuchElementException("error.comment.id");

    }
}
