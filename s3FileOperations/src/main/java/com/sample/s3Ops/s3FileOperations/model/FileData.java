package com.sample.s3Ops.s3FileOperations.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class FileData {

    private String fileName;
    private String fileType;
    private long fileSize;
}
