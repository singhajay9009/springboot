package com.ajay.spring.demo.exceptions;

public class EmployeeNotFoundException extends RuntimeException{
    public EmployeeNotFoundException(int id){
        super("Could not find employee with id: " + id);
    }

}
