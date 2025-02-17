package com.challenge.bank.repository.entity;

import com.challenge.bank.service.enums.TransactionType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;
import java.time.LocalDateTime;

/**
 * Class that represents the transactions made in the banking system
 *
 */
@Getter
@Setter
@Builder
@Table
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID transactionId;
    private UUID accountId;
    private UUID recipientAccountId;
    private double amount;
    private LocalDateTime timestamp;
    private TransactionType type;

}