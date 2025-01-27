package com.challenge.bank.adapter.entity;

import com.challenge.bank.domain.enums.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
import java.time.LocalDateTime;

/**
 * Class that represents the transactions made in the banking system
 *
 */
@Getter
@Setter
@Builder
@Entity
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID accountId;
    private double amount;
    private LocalDateTime timestamp;
    private TransactionType type;

}