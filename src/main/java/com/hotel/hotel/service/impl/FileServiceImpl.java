package com.hotel.hotel.service.impl;

import com.hotel.hotel.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    /**
     * Este metodo permite guardar una imagen en el servidor
     *
     * @param file      es la imagen que se va a subir
     * @param directory es el directorio al que se va a subir
     * @return la ruta completa de la imagen en el servidor en formato String
     */
    @Override
    public String uploadImage(MultipartFile file, String directory) {
        try {
            //obtenemos un random uuid para la foto
            String fileName = UUID.randomUUID().toString();
            byte[] bytes = file.getBytes();
            //obtenemos el nombre original de la foto
            String fileOriginalnName = file.getOriginalFilename();

            //comprobamos que el tamaño del archivo sea el correcto
            long fileSize = file.getSize();
            long maxSize = 5 * 1024 * 1024;
            if (fileSize > maxSize) {
                throw new IllegalArgumentException("error.imagen.tamano");
            }
            if (!file.getOriginalFilename().endsWith(".jpg") && !file.getOriginalFilename().endsWith(".jpeg") && !file.getOriginalFilename().endsWith(".png")) {
                throw new IllegalArgumentException("error.imagen.formato");
            }
            //creamos el nombre del archivo
            String fileExtension = fileOriginalnName.substring(fileOriginalnName.lastIndexOf("."));
            String newFileName = fileName + fileExtension;
            //creamos la carpeta y comprobamos que existe
            File folder = new File(directory);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            Path path = Paths.get(folder + "//" + newFileName);
            Files.write(path, bytes);
            return String.valueOf(path);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return "Fallo";
    }

    /**
     * Este metodo permite borrar una imagen
     *
     * @param image es la ruta de la imagen que queremos borrar
     */
    @Override
    public void remove(String image) {
        if (image == null) {
            return;
        }
        File file = new File(image);
        file.delete();
    }
}
