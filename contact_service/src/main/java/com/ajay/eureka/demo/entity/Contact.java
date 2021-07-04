package com.ajay.eureka.demo.entity;

import lombok.Data;

@Data
public class Contact {

    private int contactId;
    private String email;
    private int phone;
    private Long userId;


    public Contact(int contactId, String email, int phone, Long userId) {
        this.contactId = contactId;
        this.email = email;
        this.phone = phone;
        this.userId = userId;
    }


}
