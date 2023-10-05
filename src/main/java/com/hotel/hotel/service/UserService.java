package com.hotel.hotel.service;

import com.hotel.hotel.dto.InsertUserDTO;
import com.hotel.hotel.dto.LoginUserDTO;
import com.hotel.hotel.dto.UserDTO;
import com.hotel.hotel.entity.User;

import java.util.List;
import java.util.Locale;

public interface UserService {
    UserDTO insert(InsertUserDTO user);

    User login(LoginUserDTO dto);

    User findById(long parseLong);

    List<UserDTO> getAllUsers();

    UserDTO findByIdAndConvert(Long id);

    void delete(Long id);

    UserDTO update(UserDTO userDTO, Long id);

    String updatePassword(String password, Long id);
}
