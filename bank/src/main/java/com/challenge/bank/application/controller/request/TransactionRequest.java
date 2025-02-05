package com.challenge.bank.application.controller.request;

import com.challenge.bank.domain.enums.TransactionType;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TransactionRequest {

    @NonNull
    private UUID accountId;
    @NonNull
    private UUID recipientAccountId;
    @Positive
    private double amount;
    @NonNull
    private LocalDateTime timestamp;
    @NonNull
    private TransactionType type;

}
