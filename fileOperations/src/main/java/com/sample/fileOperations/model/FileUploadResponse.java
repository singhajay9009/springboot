package com.sample.fileOperations.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter @Getter
public class FileUploadResponse {

    private String fileName;
    private String fileDownloadUrl;
    private String fileType;
    private long fineSize;

}
