package com.ajay.spring.demo.exceptions;

public class NoDataFoundException extends RuntimeException{

    public NoDataFoundException() {
        super("No data found...");
    }

}
