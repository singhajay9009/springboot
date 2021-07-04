package com.ajay.eureka.demo.service;


import com.ajay.eureka.demo.entity.Contact;
import com.ajay.eureka.demo.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService implements ContactRepository {
    //fake data
    List<Contact> contacts = List.of(
            new Contact(1,"john.wright@gmail.com", 3424324, 1L),
            new Contact(2,"john.wright@icloud.com", 777777, 1L),
            new Contact(1,"alice.green@gmail.com", 5555555, 2L),
            new Contact(1,"rick.oliver@gmail.com", 7676767, 1L)
    );

    @Override
    public List<Contact> getContactOfEmployee(Long userId) {
        return contacts.stream().filter(c -> c.getUserId().equals(userId)).collect(Collectors.toList());
    }
}
