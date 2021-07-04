package com.ajay.eureka.demo.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Employee {

    private Long id;
    private String name;
    private int age;
    private List<Contact> contacts = new ArrayList<>();

    public Employee(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
