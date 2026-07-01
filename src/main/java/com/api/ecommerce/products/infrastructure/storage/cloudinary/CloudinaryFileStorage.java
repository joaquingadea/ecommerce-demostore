package com.api.ecommerce.products.infrastructure.storage.cloudinary;

import com.api.ecommerce.products.domain.FileStorage;
import com.cloudinary.Cloudinary;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@ConditionalOnProperty(
        name = "storage.provider",
        havingValue = "cloudinary"
)
public class CloudinaryFileStorage implements FileStorage {

    private final Cloudinary cloudinary;

    public CloudinaryFileStorage(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String upload(MultipartFile file) {
        return "";
    }

    @Override
    public void delete(String fileId) {

    }

    @Override
    public String getUrl(String fileId) {
        return "";
    }
}
