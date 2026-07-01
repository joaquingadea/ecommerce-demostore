package com.api.ecommerce.products.infrastructure.storage;

import com.api.ecommerce.products.domain.FileStorage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@ConditionalOnProperty(
        name = "storage.provider",
        havingValue = "local"
)
public class LocalFileStorageService implements FileStorage {

    private final String uploadDir = "uploads/";

    @Override
    public String upload(MultipartFile file){
        try {

            // random ID + nombre del archivo (evita que se guarden archivos con nombre igual)
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            // path = "uploads/fileName"
            Path path = Paths.get(uploadDir + fileName);

            // toma la carpeta padre y si no existe la crea
            Files.createDirectories(path.getParent());

            // escribe en el path guardado los bytes del archivo
            Files.write(path, file.getBytes());

            return "/uploads/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void delete(String imagePath){
        try {
            Path path = Paths.get(imagePath);
            Files.deleteIfExists(path);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getUrl(String fileId) {
        return "";
    }
}
