package com.tienda.tienda;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Slf4j
@Configuration
public class StorageConfig {

    @Value("${firebase.json.path}")
    private String jsonPath;

    @Value("${firebase.json.file}")
    private String jsonFile;

    @Bean
    public Storage storage() {
        ClassPathResource resource = new ClassPathResource(jsonPath + File.separator + jsonFile);
        if (!resource.exists()) {
            log.warn("No se encontró el archivo de credenciales de Firebase ({}/{}). "
                    + "La subida de imágenes a Firebase quedará deshabilitada hasta que se agregue.",
                    jsonPath, jsonFile);
            return null;
        }
        try (InputStream inputStream = resource.getInputStream()) {
            GoogleCredentials credentials = GoogleCredentials.fromStream(inputStream);
            return StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        } catch (IOException e) {
            log.warn("No fue posible inicializar Firebase Storage. "
                    + "La subida de imágenes a Firebase quedará deshabilitada.", e);
            return null;
        }
    }

}
