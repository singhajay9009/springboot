package com.sample.fileOperations.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "file")
@Configuration
@Setter
@Getter
public class FileStorageProperties {

    private String uploadDir;

    public String getUploadDir(){
        return this.uploadDir;
    }
}
