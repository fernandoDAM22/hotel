package com.hotel.hotel.repository;

import com.hotel.hotel.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {
    List<Room> findByCapacity(Long capacity);
    List<Room> findByPriceBetween(int min, int max);

    List<Room> findByTypeIn(List<String> types);
}
