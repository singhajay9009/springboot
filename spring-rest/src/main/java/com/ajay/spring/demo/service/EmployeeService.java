package com.ajay.spring.demo.service;

import com.ajay.spring.demo.entity.Employee;
import com.ajay.spring.demo.exceptions.EmployeeNotFoundException;
import com.ajay.spring.demo.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public Employee getEmployee(int id){
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee.orElseThrow(() -> new EmployeeNotFoundException(id));
    }


}