package com.sample.fileOperations.exceptions;

import org.apache.tomcat.jni.File;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(FileUploadException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String fileUploadExceptionHandler(FileUploadException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(FileDeleteException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String fileDeleteExceptionHandler(FileDeleteException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(FileDownloadException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String fileUploadExceptionHandler(FileDownloadException ex){
        return ex.getMessage();
    }

}
