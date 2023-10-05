package com.hotel.hotel.components;

import com.hotel.hotel.dto.ErrorDTO;
import com.hotel.hotel.exception.*;
import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;


@RestControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler {
    /**
     * Instancia del componente que nos permite acceder a los mensajes del archivo
     * messages.properties
     */
    private final MessageComponent message;

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorDTO> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(HttpStatus.BAD_REQUEST);
        errorDTO.setMessage(this.message.getMessage("error.request.header"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorDTO> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(HttpStatus.BAD_REQUEST);
        errorDTO.setMessage(this.message.getMessage("error.request.parameters"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorDTO> handleDuplicateUserException(DuplicateUserException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(HttpStatus.CONFLICT);
        errorDTO.setMessage(this.message.getMessage(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDTO);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDTO> handleBadRequestException(BadRequestException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(HttpStatus.BAD_REQUEST);
        errorDTO.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }
    @ExceptionHandler(BadLoginExcepcion.class)
    public ResponseEntity<ErrorDTO> handleBadLoginException(BadLoginExcepcion ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(HttpStatus.CONFLICT);
        errorDTO.setMessage(this.message.getMessage(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(errorDTO);
    }
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorDTO> handleForbiddenException(ForbiddenException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(HttpStatus.FORBIDDEN);
        errorDTO.setMessage(this.message.getMessage(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDTO);
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorDTO> handleNoSuchElementException(NoSuchElementException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(HttpStatus.NOT_FOUND);
        errorDTO.setMessage(this.message.getMessage(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handleIlegalArgumentException(IllegalArgumentException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(HttpStatus.BAD_REQUEST);
        errorDTO.setMessage(this.message.getMessage(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }
    @ExceptionHandler(RoomNotAvailableException.class)
    public ResponseEntity<ErrorDTO> handleRoomNotAvailableException(RoomNotAvailableException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(HttpStatus.BAD_REQUEST);
        errorDTO.setMessage(this.message.getMessage(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDTO);
    }

}
