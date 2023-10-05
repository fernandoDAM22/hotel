package com.hotel.hotel.components;

import com.hotel.hotel.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ErrorUtils {

    /**
     * Instancia del componente que permite acceder a los mensajes del archivo
     * messages.properties
     */
    @Autowired
    private MessageComponent message;

    /**
     * Este metodo permite obtener todos los errores de validacion
     * @param bindingResult es el objeto que contiene todos los errores de validacion
     * @return una lista con todos los errores de validacion
     */
    public List<String> getErrorMessages(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .map(this::resolveErrorMessage)
                .collect(Collectors.toList());
    }

    /**
     * Este metodo permite obtener el mensaje de validacion a traves de su clave
     * @param fieldError la clave del mensaje de error
     * @return el mensaje de error con la clave especificada
     */
    private String resolveErrorMessage(FieldError fieldError) {
        return message.getMessage(fieldError.getDefaultMessage());
    }

    /**
     * Este metodo permite lanzar una excepcion en caso de que las validaciones no sean correctas
     * @param bindingResult es el objeto que contiene los errores de validacion
     */
    public void handle(BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(getErrorMessages(bindingResult).toString());
        }
    }
}

