package com.hotel.hotel.repository;

import com.hotel.hotel.entity.Reservation;
import com.hotel.hotel.other.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query(value = """
            SELECT COUNT(*)
            FROM reservation
            WHERE room_id = :roomId
            AND status <> 'CANCELADA'
            AND (
                (entry_date <= :exitDate AND exit_date >= :entryDate) OR
                (entry_date <= :entryDate AND exit_date >= :entryDate) OR
                (entry_date >= :entryDate AND exit_date <= :exitDate)
            );
                        
            """, nativeQuery = true)
    int isAvailable(@Param("roomId") Long roomId, @Param("entryDate") Date entryDate, @Param("exitDate") Date exitDate);

    List<Reservation> findByRoomId(Long roomId);

    List<Reservation> findByUserId(Long userId);

    List<Reservation> findByStatusIn(List<Status> status);
}
