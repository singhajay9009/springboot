package com.ajay.eureka.demo.service;

import com.ajay.eureka.demo.entity.Contact;
import com.ajay.eureka.demo.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class EmployeeInfoServiceHystrix {

    @Autowired
    EmployeeInfoService employeeInfoService;

    @Autowired
    RestTemplate restTemplate;


    public Employee getEmployee(Long id){

            Employee employee = employeeInfoService.getEmployee(id);
            List<Contact> contacts = restTemplate.getForObject("http://contact-service/contact/" + id, List.class);

            employee.setContacts(contacts);
            return employee;
    }
}
