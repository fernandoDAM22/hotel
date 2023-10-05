package com.hotel.hotel.service;

import com.hotel.hotel.dto.InsertRoomDTO;
import com.hotel.hotel.dto.RoomDTO;
import com.hotel.hotel.entity.Room;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface RoomService {
    RoomDTO insert(InsertRoomDTO dto, MultipartFile file,String path);

    List<RoomDTO> getAllRooms();

    RoomDTO findByIdAndConvert(Long id);
    Room findById(Long id);

    List<RoomDTO> findByTypeIn(List<String> types);

    List<RoomDTO> findByCapacity(Long capacity);

    List<RoomDTO> findByPrice(int min, int max);

    RoomDTO update(RoomDTO dto);

    RoomDTO updateImage(Long id, MultipartFile file,String path);

}
