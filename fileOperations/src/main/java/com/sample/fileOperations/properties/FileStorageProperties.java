package com.sample.fileOperations.properties;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileStorageProperties {

    @Value("${upload-dir}")
    private String uploadDir;

    public String getUploadDir(){
        return this.uploadDir;
    }
}
