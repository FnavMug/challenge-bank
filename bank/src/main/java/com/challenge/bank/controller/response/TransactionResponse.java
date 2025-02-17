package com.challenge.bank.controller.response;

import com.challenge.bank.service.enums.TransactionType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TransactionResponse {
    private UUID transactionId;
    private UUID accountId;
    private UUID recipientAccountId;
    private double amount;
    private LocalDateTime timestamp;
    private TransactionType type;

}
