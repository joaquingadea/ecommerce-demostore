package com.api.ecommerce.products.infrastructure.storage.cloudinary;

import com.api.ecommerce.products.domain.FileStorage;
import com.api.ecommerce.products.domain.ImageData;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@ConditionalOnProperty(
        name = "storage.provider",
        havingValue = "cloudinary"
)
public class CloudinaryFileStorage implements FileStorage {

    private final Cloudinary cloudinary;
    private static final String PRODUCTS_FOLDER = "products";
    public CloudinaryFileStorage(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public ImageData upload(MultipartFile file) {
        try {
            Map<String, Object> options = new HashMap<>();
            options.put("folder", PRODUCTS_FOLDER);


            Map<?, ?> result = cloudinary.uploader()
                    .upload(
                            file.getBytes(),
                            options
                    );

            return new ImageData(
                    result.get("public_id").toString(),
                    result.get("secure_url").toString()
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
