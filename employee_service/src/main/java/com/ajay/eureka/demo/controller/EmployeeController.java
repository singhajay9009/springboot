package com.ajay.eureka.demo.controller;


import com.ajay.eureka.demo.entity.Contact;
import com.ajay.eureka.demo.entity.Employee;
import com.ajay.eureka.demo.service.EmployeeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeInfoService employeeInfoService;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable("id") Long id){

        Employee employee = employeeInfoService.getEmployee(id);
        List<Contact> contacts = restTemplate.getForObject("http://contact-service/contact/" + id, List.class);

        employee.setContacts(contacts);
        return employee;
    }
}
