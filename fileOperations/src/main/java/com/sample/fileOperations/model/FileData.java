package com.sample.fileOperations.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
@Builder
public class FileData {

    private String fileName;
    private String fileType;
    private long fileSize;

}
