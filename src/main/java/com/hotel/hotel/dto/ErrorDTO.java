package com.hotel.hotel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@Getter @Setter
public class ErrorDTO {
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/mm/yyyy hh:mm:ss")
    private LocalDateTime date = LocalDateTime.now();
    private String message;
}
