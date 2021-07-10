package com.ajay.spring.demo.controller;

import com.ajay.spring.demo.entity.Employee;
import com.ajay.spring.demo.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/{id}")
    public ResponseEntity<Employee> fetchEmployee(@PathVariable("id") int id){
        Employee employee = employeeService.getEmployee(id);
       // return new ResponseEntity<>(employee, HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.OK).body(employee);
    }


}
