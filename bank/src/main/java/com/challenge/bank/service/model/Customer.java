package com.challenge.bank.service.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Customer {

    private UUID customerId;
    private String name;
    private String email;
    private String phoneNumber;
    private String doc;

}
