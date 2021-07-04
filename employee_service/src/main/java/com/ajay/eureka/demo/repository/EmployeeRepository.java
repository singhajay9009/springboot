package com.ajay.eureka.demo.repository;

import com.ajay.eureka.demo.entity.Employee;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository {

    Employee getEmployee(Long id);
}
