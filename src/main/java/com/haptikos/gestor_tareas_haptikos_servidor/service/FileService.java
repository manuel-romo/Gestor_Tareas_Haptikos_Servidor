package com.haptikos.gestor_tareas_haptikos_servidor.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class FileService {

    private final Cloudinary cloudinary;

    public FileService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile file) throws IOException {
        // Subida de archivo a Cloudinary
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        // Extrae la URL segura que da Cloudinary
        System.out.println("LA URL DEVUELTA POR EL SERVICIO ES: " + uploadResult.get("secure_url").toString());

        return uploadResult.get("secure_url").toString();
    }
}