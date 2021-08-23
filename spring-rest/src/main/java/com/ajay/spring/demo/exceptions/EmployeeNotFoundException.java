package com.ajay.spring.demo.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmployeeNotFoundException extends RuntimeException{
    public EmployeeNotFoundException(int id){
        super("Could not find employee with id: " + id);
        log.error("Could not find employee with id: {}", id);
    }

    public EmployeeNotFoundException(){
        super("Could not find employees" );

    }
}
