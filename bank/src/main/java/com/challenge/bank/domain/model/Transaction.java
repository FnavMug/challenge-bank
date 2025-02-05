package com.challenge.bank.domain.model;

import com.challenge.bank.domain.enums.TransactionType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Transaction {

    private UUID transactionId;
    private UUID accountId;
    private UUID recipientAccountId;
    private double amount;
    private LocalDateTime timestamp;
    private TransactionType type;

}
