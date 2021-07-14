package com.ajay.spring.demo.controller;

import com.ajay.spring.demo.entity.Employee;
import com.ajay.spring.demo.exceptions.EmployeeNotFoundException;
import com.ajay.spring.demo.model.EmployeeModelAssembler;
import com.ajay.spring.demo.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.hibernate.EntityMode;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/*
 -----
 1. Demonstrating REST APIs
 2. HATEOAS ( Hyper Text As The Engine of Application State )

 */
@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeModelAssembler employeeModelAssembler;

    @GetMapping("/")
    public ResponseEntity<?> fetchEmployees(){
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    // Another way of creating
    @GetMapping("/{id}")
    public EntityModel<Employee> fetchEmployee(@PathVariable("id") int id){
        Employee employee = employeeService.getEmployee(id);

       // return new ResponseEntity<>(employee, HttpStatus.OK);
       // return ResponseEntity.status(HttpStatus.OK).body(employee);

//        return EntityModel.of(employee,
//                linkTo(methodOn(EmployeeController.class).fetchEmployee(id)).withSelfRel(),
//                linkTo(methodOn(EmployeeController.class).fetchEmployees()).withRel("employees"));

        return employeeModelAssembler.toModel(employee);

    }

    @GetMapping("/all")
    CollectionModel<EntityModel<Employee>> all() {

        List<EntityModel<Employee>> employees = employeeService.getAllEmployees().stream() //
                .map(employeeModelAssembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
    }

    @PostMapping("/create")
    ResponseEntity<?> createEmployee(@RequestBody Employee employee){

/*  ---- Setting up LocalDateTime ( 2 ways ) ---------
        //1 - default time pattern
        String time = "2019-03-27T10:15:30";
        LocalDateTime localTimeObj = LocalDateTime.parse(time);

        //2 - specific date time pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");
        String time1 = "2019-03-27 10:15:30 AM";
        LocalDateTime localTimeObj1 = LocalDateTime.parse(time1, formatter);
    */

        String time = "2012-03-27T10:15:30";
        LocalDateTime localTimeObj = LocalDateTime.parse(time);

        //Set up employee obj
        /*
        Employee emp = new Employee();
        emp.setName("Kevin Roach");
        emp.setAge(44);
        emp.setJoiningDate(localTimeObj);
        emp.setSalary(20000);
        emp.setActive(true);
         */
        /*
            //   Payload   //
                {
                 "name" : "Alice Green",
                  "age" : 22,
                  "salary" : 12000,
                  "joiningDate" : "2011-01-22T19:10:25",
                  "active" : true
                }
         */

        Employee emp = new Employee();
        emp.setName(employee.getName());
        emp.setSalary(employee.getSalary());
        emp.setJoiningDate(employee.getJoiningDate());
        emp.setAge(employee.getAge());
        emp.setActive(employee.isActive());



        EntityModel<Employee> entityModel =
                employeeModelAssembler.toModel(employeeService.createEmployee(emp));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id){

        Optional.ofNullable(employeeService.getEmployee(id)).orElseThrow(() -> new EmployeeNotFoundException(id));
        employeeService.deleteEmployee(id);
        return ResponseEntity.status(HttpStatus.OK).body("record deleted");
    }






}
