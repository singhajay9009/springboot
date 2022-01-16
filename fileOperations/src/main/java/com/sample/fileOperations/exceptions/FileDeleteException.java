package com.sample.fileOperations.exceptions;

public class FileDeleteException extends RuntimeException{

    public FileDeleteException(String message){
        super(message);
    }

    public FileDeleteException(String message, Throwable cause){
        super(message, cause);
    }
}
