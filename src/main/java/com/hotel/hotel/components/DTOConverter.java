package com.hotel.hotel.components;

import com.hotel.hotel.dto.*;
import com.hotel.hotel.entity.*;
import com.hotel.hotel.other.Status;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DTOConverter {
    /**
     * Instancia de ModelMapper para realizar las conversiones
     */
    private final ModelMapper modelMapper;

    /**
     * Este metodo permite convertir un InsertUserDTO a User
     *
     * @param userDTO el dto que queremos convertir
     * @return un objeto User con los datos del dto
     */
    public User convertToUSer(InsertUserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    /**
     * Este metodo permite convertir un usuario en DTO
     *
     * @param user es el usuario que queremos convertir
     * @return un Objeto UserDTO con los datos del usuario
     */
    public UserDTO convertToUserDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
    /**
     * Este metodo permite convertir un UserLoginDto en User
     *
     * @param dto es el dto que queremos convertir
     * @return un Objeto User con los datos del usuario
     */
    public User convertDTOLoginToUser(LoginUserDTO dto) {
        return modelMapper.map(dto, User.class);
    }

    /**
     * Este metodo permite convertirt un InsertRoomDTO a Room
     * @param dto es el dto que se quiere convertir
     * @return una instancia de la clase Room con los datos del dto
     */
    public Room convertToRoom(InsertRoomDTO dto){
        return modelMapper.map(dto, Room.class);
    }

    /**
     * Este metodo permite convertir una instancia de la clase Room a RoomDTO
     * @param room es el objeto que se quiere convertir
     * @return una instancia de la clase RoomDTO con los datos del objeto room
     */
    public RoomDTO convertRoomToDTO(Room room){
        return modelMapper.map(room,RoomDTO.class);
    }

    /**
     * Este metodo permite convertir dto a instancia de la clase room
     * @param dto es el dto que se quiere convertir
     * @return una instancia de la clase room con los datos del dto
     */
    public Room convertToRoom(RoomDTO dto){
        return modelMapper.map(dto, Room.class);
    }

    /**
     * Este metodo permite convertir un InsertReservationDTO a Reservation
     * @param dto el dto con los datos
     * @return una instancia de la clase Reservation con los datos del dto
     */
    public Reservation convertDTOToReservation(InsertReservationDTO dto){
        Reservation reservation = new Reservation();
        reservation.setEntryDate(dto.getEntryDate());
        reservation.setExitDate(dto.getExitDate());
        reservation.setCreationDate(LocalDateTime.now());
        reservation.setStatus(Status.PENDIENTE);
        return reservation;
    }

    /**
     * Este metodo permite convertir una instancia de la clase Reservation a dto
     * @param dto es el dto con los datos
     * @return un dto con los datos de la reserva
     */
    public ReservationDTO convertReservationToDTO(Reservation dto){
        return ReservationDTO.builder()
                .user(dto.getUser().getName())
                .entryDate(dto.getEntryDate())
                .exitDate(dto.getExitDate())
                .room(Math.toIntExact(dto.getRoom().getId()))
                .totalPrice(dto.getTotalPrice())
                .status(dto.getStatus())
                .creationDate(dto.getCreationDate())
                .build();
    }

    /**
     * Este metodo permite convertir un InsertCommentDTO a una instancia de la clase Comment
     * @param dto es el dto que queremos convertir
     * @return una instancia de la clase Comment con los datos del dto
     */
    public Comment convertDTOToComment(InsertCommentDTO dto){
        Comment comment = new Comment();
        comment.setQualification(dto.getQualification());
        comment.setComment(dto.getComment());
        return comment;
    }

    /**
     * Este metodo permite convertir una instancia de la clase Comment a DTO
     * @param comment es el objeto que queremos convertir a dto
     * @return un dto con los datos del comentario
     */
    public CommentDTO convertCommentToDTO(Comment comment){
        return CommentDTO.builder()
                .user(comment.getUser().getName())
                .room(comment.getRoom().getId())
                .qualification(comment.getQualification())
                .comment(comment.getComment())
                .date(comment.getDate())
                .build();
    }
}
