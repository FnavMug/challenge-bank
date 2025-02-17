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
 * Class that represents the customers in the banking system
 */
@Getter
@Setter
@Builder
@Table
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID customerId;
    private String name;
    private String email;
    private String phoneNumber;
    private String doc;

}