package com.ajay.eureka.demo.service;

import com.ajay.eureka.demo.entity.Employee;
import com.ajay.eureka.demo.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeInfoService implements EmployeeRepository {

    // retrieve data
    List<Employee> listEmp = List.of(
            new Employee(1L, "John Wright", 56 ),
            new Employee(2L, "Alice Green", 24 ),
            new Employee(3L, "Rick Oliver", 34)
    );

    @Override
    public Employee getEmployee(Long id) {
        return listEmp.stream().filter(e -> e.getId().equals(id)).findAny().orElse(null);
    }
}
