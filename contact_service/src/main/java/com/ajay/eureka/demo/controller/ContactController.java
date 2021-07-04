package com.ajay.eureka.demo.controller;

import com.ajay.eureka.demo.entity.Contact;
import com.ajay.eureka.demo.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    ContactService contactService;

    @GetMapping("/{empId}")
    public List<Contact> getEmployeeContacts(@PathVariable("empId") Long empId){
        return contactService.getContactOfEmployee(empId);
    }
}
