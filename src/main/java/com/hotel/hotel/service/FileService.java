package com.hotel.hotel.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadImage(MultipartFile file, String directory);

    void remove(String image);
}
