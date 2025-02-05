package com.challenge.bank.application.controller;

import com.challenge.bank.application.controller.request.CustomerRequest;
import com.challenge.bank.application.controller.response.AccountResponse;
import com.challenge.bank.application.controller.response.TransactionResponse;
import com.challenge.bank.domain.mapper.CustomerMapper;
import com.challenge.bank.domain.service.BankingService;
import jakarta.validation.Valid;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/banking")
public class BankController {

    private final BankingService bankingService;

    private static final CustomerMapper CustomerMapper = Mappers.getMapper(CustomerMapper.class);

    public BankController(BankingService bankingService) {
        this.bankingService = bankingService;
    }

    /**
     * Creates a new customer in the db
     * @param customerRequest the customer to be created, requires non-null fields
     */
    @PostMapping("/createCustomer")
    public void createCustomer(@Valid @RequestBody CustomerRequest customerRequest) {

        bankingService.createCustomer(CustomerMapper.toModel(customerRequest));

    }

    public void createAccount() {

    }

    public TransactionResponse deposit() {
        return null;
    }

    public TransactionResponse withdraw() {
        return null;
    }

    public TransactionResponse transfer() {
        return null;
    }

    public AccountResponse getBalance() {
        return null;
    }
}
