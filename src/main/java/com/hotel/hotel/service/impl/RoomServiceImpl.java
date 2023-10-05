package com.hotel.hotel.service.impl;

import com.hotel.hotel.components.DTOConverter;
import com.hotel.hotel.components.MessageComponent;
import com.hotel.hotel.dto.InsertRoomDTO;
import com.hotel.hotel.dto.RoomDTO;
import com.hotel.hotel.entity.Room;
import com.hotel.hotel.repository.RoomRepository;
import com.hotel.hotel.service.FileService;
import com.hotel.hotel.service.ReservationService;
import com.hotel.hotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    /**
     * Instancia del convertidor de dtos
     */
    private final DTOConverter converter;
    /**
     * Instancia del servicio de imagenes
     */
    private final FileService fileService;
    /**
     * Instancia del repositorio
     */
    private final RoomRepository repository;


    /**
     * Este metodo permite insertar una habitacion
     *
     * @param dto  es el dto con los datos de la habitacion a insertar
     * @param file es la foto de la habitacion
     * @param path es la ruta donde se va a guardar la imagen
     * @return un dto con los datos de la aplicacion insertada
     */
    @Override
    public RoomDTO insert(InsertRoomDTO dto, MultipartFile file, String path) {
        Room room = converter.convertToRoom(dto);
        String image_path = fileService.uploadImage(file, path);
        room.setImage(image_path);
        return converter.convertRoomToDTO(repository.save(room));
    }

    /**
     * Este metodo permite obtener todas las habitaciones de la base de datos
     *
     * @return una lista de dtos con los datos de todas las habitaciones
     */
    @Override
    public List<RoomDTO> getAllRooms() {
        List<Room> rooms = repository.findAll();
        return rooms.stream()
                .map(converter::convertRoomToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Este metodo permite obtener un dto con los datos de una habitacion
     *
     * @param id es el id de la habitacion que queremos obtener
     * @return un dto con los datos de la habitacion indicada
     */
    @Override
    public RoomDTO findByIdAndConvert(Long id) {
        Room room = repository.findById(id).orElseThrow(() -> new NoSuchElementException("error.room.id"));
        return converter.convertRoomToDTO(room);
    }

    /**
     * Este metodo permite obtener una habitacion por su id
     * @param id es el id de la habitacion que queremos obtener
     * @return la habitacion con el id indicado
     */
    @Override
    public Room findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchElementException("error.room.id"));
    }

    /**
     * Este metodo permite obtener todas las habitaciones de la base de datos cuyo tipo se encuentre en
     * la lista indicada
     *
     * @param types es la lista de tipos
     * @return una lista de dtos con las habitaciones cuyo tipo se encuentre en la
     * lista indicada
     */
    @Override
    public List<RoomDTO> findByTypeIn(List<String> types) {
        List<Room> rooms = repository.findByTypeIn(types);
        return rooms.stream()
                .map(converter::convertRoomToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Este metodo permite obtener una lista de dtos con los datos de las habitaciones
     * de una determinada capacidad
     *
     * @param capacity es la capacidad de la habitacion que queremos obtener
     * @return una lista de dtos con los datos de todas las habitaciones de la capacidad indicada
     */
    @Override
    public List<RoomDTO> findByCapacity(Long capacity) {
        List<Room> rooms = repository.findByCapacity(capacity);
        return rooms.stream()
                .map(converter::convertRoomToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Este metodo permite obtener una lista de dtos con los datos de las habitaciones
     * cuyo precio se encuentre en el rango indicado
     *
     * @param min es el precio minimo del rango
     * @param max es el precio maximo del rango
     * @return una lista de dtos con los datos de todas las habitaciones cuyo precio se encuentre en el rango indicado
     */
    @Override
    public List<RoomDTO> findByPrice(int min, int max) {
        List<Room> rooms = repository.findByPriceBetween(min, max);
        return rooms.stream()
                .map(converter::convertRoomToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Este metodo permite actualizar una habitacion a excepcion de su imagen
     * @param dto es el dto con los datos de la habitacion que queremos modificar
     * @return un dto con los nuevos datos de la habitacion
     */
    @Override
    public RoomDTO update(RoomDTO dto) {
        Room room = repository.findById(dto.getId()).orElseThrow(() -> new NoSuchElementException("error.room.id"));
        room.setType(dto.getType());
        room.setCapacity(dto.getCapacity());
        room.setPrice(dto.getPrice());
        room.setDescripcion(dto.getDescripcion());
        return converter.convertRoomToDTO(repository.save(room));
    }

    /**
     * Este metodo permite modificar ls imagen de una habitacion
     * @param id es el id de la habitacion a la que le queremos modificar la imagen
     * @param file es la nueva imagen de la habitacion
     * @param path es la ruta donde se va a guardar la imagen
     * @return un dto con los datos de la habitacion modificada
     */
    @Override
    public RoomDTO updateImage(Long id, MultipartFile file, String path) {
        Room room = repository.findById(id).orElseThrow(() -> new NoSuchElementException("error.room.id"));
        fileService.remove(room.getImage());
        String image = fileService.uploadImage(file,path);
        room.setImage(image);
        return converter.convertRoomToDTO(repository.save(room));
    }


}
