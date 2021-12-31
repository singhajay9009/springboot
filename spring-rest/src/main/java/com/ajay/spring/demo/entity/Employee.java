package com.ajay.spring.demo.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Setter @Getter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int age;
    private int salary;
    private LocalDateTime joiningDate;
    private boolean active;

    public Employee(){

    }

    public Employee(int id, String name, int age, int salary, LocalDateTime joiningDate, boolean active) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.joiningDate = joiningDate;
        this.active = active;
    }

//    @Override
//    public boolean equals(Object o){
//        if(this == o){
//            return true;
//        }
//        if(!(o instanceof Employee)){
//            return false;
//        }
//        Employee employee = (Employee) o;
//        return Objects.equals(this.id, employee.id)
//                && Objects.equals(this.name, employee.name)
//                && Objects.equals(this.age, employee.age)
//                && Objects.equals(this.joiningDate, employee.joiningDate)
//                && Objects.equals(this.salary, employee.salary)
//                && Objects.equals(this.active, employee.active);
//    }
//
//    @Override
//    public int hashCode(){
//        return Objects.hash(this.id, this.name, this.salary, this.age,
//            this.joiningDate, this.active);
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id && age == employee.age && salary == employee.salary && active == employee.active && Objects.equals(name, employee.name) && Objects.equals(joiningDate, employee.joiningDate);
    }


    @Override
    public int hashCode() {

        return Objects.hash(id, name, age, salary, joiningDate, active);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name +
                ", age=" + age +
                ", salary=" + salary +
                ", joiningDate=" + joiningDate +
                ", active=" + active +
                '}';
    }
}
