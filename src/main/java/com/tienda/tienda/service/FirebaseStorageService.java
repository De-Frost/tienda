package com.tienda.tienda.service;

import java.io.IOException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FirebaseStorageService {

    // Integración real con Firebase deshabilitada por ahora
    public String uploadImage(MultipartFile localFile, String folder, Integer id) throws IOException {
        return null;
    }
}