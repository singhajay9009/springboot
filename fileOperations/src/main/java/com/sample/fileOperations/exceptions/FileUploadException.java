package com.sample.fileOperations.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUploadException extends RuntimeException{

    public FileUploadException(String message){
        super(message);
    }
    public FileUploadException(String message, Throwable cause){
        super(message, cause);
        log.error(message, cause.getMessage());
    }
}
