package com.hotel.hotel.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

@Configuration
public class MessageComponent {
    /**
     * Componente que nos permite tener acceso al archivo de mensajes
     */
    @Autowired
    private MessageSource messageSource;

    /**
     * Este metodo nos permite obtener un mensaje del archivo de mensajes
     * @param key es la clave del mensaje que queremos obtener
     * @return el mensaje con la clave indicada
     */
    public String getMessage(String key){
        Locale currentLocale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, null, currentLocale);
    }
}
