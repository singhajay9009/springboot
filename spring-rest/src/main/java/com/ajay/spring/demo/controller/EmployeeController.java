package com.ajay.spring.demo.controller;

import com.ajay.spring.demo.entity.Employee;
import com.ajay.spring.demo.exceptions.EmployeeNotFoundException;
import com.ajay.spring.demo.exceptions.NoDataFoundException;
import com.ajay.spring.demo.model.EmployeeModelAssembler;
import com.ajay.spring.demo.repository.EmployeeRepository;
import com.ajay.spring.demo.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
//@Slf4j
public class EmployeeController {

    Logger log = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;
    private final EmployeeModelAssembler employeeModelAssembler;

    private final EmployeeRepository employeeRepository;

    @GetMapping("/all")
    public ResponseEntity<?> fetchEmployees(){
        List<Employee> employees = employeeService.getAllEmployees();
        Optional.ofNullable(employees).orElseThrow(NoDataFoundException::new);
        log.info("All users found");
        return ResponseEntity.ok(employees);
    }

    // With HATEOAS links. -- does not return id in response
    @GetMapping("/one/{id}")
    public EntityModel<Employee> fetchEmployee(@PathVariable int id){
        Employee employee = employeeService.getEmployee(id);
        Optional.ofNullable(employee).orElseThrow(() -> new EmployeeNotFoundException(id));
        log.info("User found with id: {}", id);
        return EntityModel.of(employee,
                linkTo(methodOn(EmployeeController.class).fetchEmployee(id)).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).fetchEmployees()).withRel("employees"));

    }

    @GetMapping("/single/{id}")
    public ResponseEntity<?> fetchSingleEmployee(@PathVariable int id){
        Employee employee = employeeService.getEmployee(id);
        log.info("User found with id for single end point: {}", id);
        return ResponseEntity.ok(employee);
    }


    @GetMapping("/allagain")
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
        log.info("user is created with id: {}", entityModel.getContent().getId());

        /* == we can return below URi in the location header to access the created
              resource  ====
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/one/{id}")
                .buildAndExpand(emp.getId())
                .toUri();
            return ResponseEntity<Employee>.created(location).build();
        */

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @PutMapping("/employee/update/{id}")
    public ResponseEntity<EntityModel> updateEmployee(@RequestBody Employee employee, @PathVariable int id){

        Employee updatedEmp = employeeRepository.findById(id)
                .map(emp -> {
                    emp.setName(employee.getName());
                    emp.setActive(employee.isActive());
                    emp.setAge(employee.getAge());
                    emp.setSalary(employee.getSalary());
                    emp.setJoiningDate(employee.getJoiningDate());
                    return employeeService.createEmployee(emp);
                }).orElseGet(() ->{
                    employee.setId(id);
                    return employeeService.createEmployee(employee);
                        }

                );

        /*  sending href to 'location' header to access employee ---
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/single/{id}")
                .buildAndExpand(id).toUri();
         return ResponseEntity.created(uri).body("employee updated");
         */


        EntityModel<Employee> entityModel = employeeModelAssembler.toModel(updatedEmp);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id){
        employeeService.deleteEmployee(id);
        log.info("User is deleted with id: {}", id);
        return ResponseEntity.status(HttpStatus.OK).body("record deleted");
    }






}
