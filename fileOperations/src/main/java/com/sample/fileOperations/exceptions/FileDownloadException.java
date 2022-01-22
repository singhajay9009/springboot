package com.sample.fileOperations.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileDownloadException extends RuntimeException{

    public FileDownloadException(String message){
        super(message);
    }
    public FileDownloadException(String message, Throwable cause){
        super(message, cause);
        log.error(message, cause.getMessage());
    }

    public FileDownloadException(Throwable cause){
        super(cause);
    }
}
