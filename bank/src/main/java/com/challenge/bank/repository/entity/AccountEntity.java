package com.challenge.bank.repository.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

/**
 * Class that represents the accounts that exist per customer
 */
@Getter
@Setter
@Builder
@Table
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID accountId;
    private String accountNumber;
    private double balance;
    private UUID customerId;
}