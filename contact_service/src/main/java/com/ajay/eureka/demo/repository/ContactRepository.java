package com.ajay.eureka.demo.repository;


import com.ajay.eureka.demo.entity.Contact;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository {

    public List<Contact> getContactOfEmployee(Long userId);
}
