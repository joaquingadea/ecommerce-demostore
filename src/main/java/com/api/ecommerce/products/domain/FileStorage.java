package com.api.ecommerce.products.domain;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorage {
    ImageData upload(MultipartFile file);
    void delete(String externalFileId);
    String getUrl(String externalFileId);
}
