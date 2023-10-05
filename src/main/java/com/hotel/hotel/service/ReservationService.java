package com.hotel.hotel.service;

import com.hotel.hotel.dto.InsertReservationDTO;
import com.hotel.hotel.dto.ReservationDTO;
import com.hotel.hotel.entity.Reservation;
import com.hotel.hotel.other.Status;

import java.util.Date;
import java.util.List;

public interface ReservationService {
    ReservationDTO reserve(InsertReservationDTO dto, Long userId);
    boolean isValidDateRange(InsertReservationDTO reservationDTO);
    boolean isAvailable(Long id, Date entry, Date exit);
    int calculateDays(Date entry, Date exit);

    List<ReservationDTO> getAllReserves();
    ReservationDTO findById(Long id,Long userId);

    List<ReservationDTO> getReservesByRoom(Long id);

    List<ReservationDTO> getReservationsByUser(Long id);

    List<ReservationDTO> findByStatus(List<Status> status);
    ReservationDTO confirm(Long id, Long userId);

    ReservationDTO cancel(Long id, Long userId);

    String getAvailability(Long id, Date entry, Date exit);
}
