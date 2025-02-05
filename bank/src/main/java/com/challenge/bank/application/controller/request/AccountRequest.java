package com.challenge.bank.application.controller.request;

import lombok.Data;

import java.util.UUID;

@Data
public class AccountRequest {
    private UUID accountId;
    private String accountNumber;
    private double balance;
    private UUID customerId;
}
