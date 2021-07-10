package com.ajay.spring.demo.controller;

import com.ajay.spring.demo.entity.Employee;
import com.ajay.spring.demo.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.hibernate.EntityMode;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/")
    public ResponseEntity<?> fetchEmployees(){
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public EntityModel<Employee> fetchEmployee(@PathVariable("id") int id){
        Employee employee = employeeService.getEmployee(id);
       // return new ResponseEntity<>(employee, HttpStatus.OK);
       // return ResponseEntity.status(HttpStatus.OK).body(employee);

        return EntityModel.of(employee,
                linkTo(methodOn(EmployeeController.class).fetchEmployee(id)).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).fetchEmployees()).withRel("employees"));
    }



}
