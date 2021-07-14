package com.ajay.spring.demo.service;

import com.ajay.spring.demo.entity.Employee;
import com.ajay.spring.demo.exceptions.EmployeeNotFoundException;
import com.ajay.spring.demo.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public Employee getEmployee(int id){
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee.orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public List<Employee> getAllEmployees(){
        List<Employee> empList = employeeRepository.findAll();
        return Optional.of(empList).orElseThrow(EmployeeNotFoundException::new);
    }

    public Employee createEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(int id){
        employeeRepository.deleteById(id);
    }

}
