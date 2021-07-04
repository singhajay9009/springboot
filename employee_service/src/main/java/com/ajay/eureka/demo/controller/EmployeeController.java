package com.ajay.eureka.demo.controller;


import com.ajay.eureka.demo.entity.Employee;
import com.ajay.eureka.demo.service.EmployeeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeInfoService employeeInfoService;

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable("id") Long id){
        return employeeInfoService.getEmployee(id);
    }
}
