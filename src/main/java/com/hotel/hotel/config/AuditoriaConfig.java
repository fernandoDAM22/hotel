package com.hotel.hotel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class AuditoriaConfig {
    /**
     * Bean de configuracion para la generacion automatica de la fecha en los comentarios
     *
     * @return un optional con la fecha actual del sistema
     */
    @Bean
    public DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(LocalDateTime.now());
    }
}
