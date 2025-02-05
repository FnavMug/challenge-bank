package com.challenge.bank.domain.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Account {
    private UUID accountId;
    private String accountNumber;
    private double balance;
    private UUID customerId;
}
