package com.challenge.bank.service;

import com.challenge.bank.adapter.entity.AccountEntity;
import com.challenge.bank.adapter.entity.TransactionEntity;
import com.challenge.bank.adapter.repository.AccountRepository;
import com.challenge.bank.adapter.repository.CustomerRepository;
import com.challenge.bank.adapter.repository.TransactionRepository;
import com.challenge.bank.domain.mapper.AccountMapper;
import com.challenge.bank.domain.mapper.TransactionMapper;
import com.challenge.bank.domain.model.Account;
import com.challenge.bank.domain.model.Transaction;
import com.challenge.bank.domain.service.BankingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BankingServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private BankingService bankingService;

    private Account accountTo;
    private Account accountFrom;
    private AccountEntity accountFromEntity;
    private AccountEntity accountToEntity;
    private Transaction transaction;
    private TransactionEntity transactionEntity;

    private static final AccountMapper AccountMapper = Mappers.getMapper(AccountMapper.class);
    private static final TransactionMapper TransactionMapper = Mappers.getMapper(TransactionMapper.class);

    @BeforeEach
    public void setUp() {
        accountTo = new Account();
        accountTo.setAccountId(UUID.randomUUID());
        accountTo.setBalance(1000.0);

        accountFrom = new Account();
        accountFrom.setAccountId(UUID.randomUUID());
        accountFrom.setBalance(1000.0);

        accountFromEntity = AccountMapper.toEntity(accountTo);
        accountToEntity = AccountMapper.toEntity(accountFrom);

        transaction = new Transaction();
        transaction.setAccountId(accountTo.getAccountId());
        transaction.setAmount(200.0);

        transactionEntity = TransactionMapper.toEntity(transaction);
    }

    @Test
    public void testDeposit() {
        // Given
        when(accountRepository.findById(any(UUID.class))).thenReturn(Mono.just(accountFromEntity));
        when(accountRepository.save(any(AccountEntity.class))).thenReturn(Mono.just(accountFromEntity));
        when(transactionRepository.save(any(TransactionEntity.class))).thenReturn(Mono.just(transactionEntity));

        // When
        Mono<Transaction> result = bankingService.deposit(transaction);

        // Then
        StepVerifier.create(result)
                .expectNextMatches(t -> t.getAmount() == 200.0)
                .verifyComplete();
    }

    @Test
    public void testWithdraw() {
        // Given
        when(accountRepository.findById(any(UUID.class))).thenReturn(Mono.just(accountFromEntity));
        when(accountRepository.save(any(AccountEntity.class))).thenReturn(Mono.just(accountFromEntity));
        when(transactionRepository.save(any(TransactionEntity.class))).thenReturn(Mono.just(transactionEntity));

        // When
        Mono<Transaction> result = bankingService.withdraw(transaction);

        // Then
        StepVerifier.create(result)
                .expectNextMatches(t -> t.getAmount() == 200.0)
                .verifyComplete();
    }

    @Test
    public void testWithdrawInsufficientBalance() {
        // Given
        transaction.setAmount(2000.0);
        when(accountRepository.findById(any(UUID.class))).thenReturn(Mono.just(accountFromEntity));

        // When
        Mono<Transaction> result = bankingService.withdraw(transaction);

        // Then
        StepVerifier.create(result)
                .expectError(IllegalArgumentException.class)
                .verify();
    }

    @Test
    public void testTransfer() {
        // Given
        Account recipientAccount = new Account();
        recipientAccount.setAccountId(UUID.randomUUID());
        recipientAccount.setBalance(500.0);

        AccountEntity recipientAccountEntity = AccountMapper.toEntity(recipientAccount);

        when(accountRepository.findById(transaction.getAccountId())).thenReturn(Mono.just(accountFromEntity));
        when(accountRepository.findById(transaction.getRecipientAccountId())).thenReturn(Mono.just(recipientAccountEntity));
        when(accountRepository.save(any(AccountEntity.class))).thenReturn(Mono.just(accountFromEntity));
        when(transactionRepository.save(any(TransactionEntity.class))).thenReturn(Mono.just(transactionEntity));

        // When
        Mono<Transaction> result = bankingService.transfer(transaction);

        // Then
        StepVerifier.create(result)
                .expectNextMatches(t -> t.getAmount() == 200.0)
                .verifyComplete();
    }

    @Test
    public void testTransferInsufficientBalance() {
        // Given
        transaction.setAmount(2000.0);
        when(accountRepository.findById(transaction.getAccountId())).thenReturn(Mono.just(accountFromEntity));
        when(accountRepository.findById(transaction.getRecipientAccountId())).thenReturn(Mono.just(accountToEntity));

        // When
        Mono<Transaction> result = bankingService.transfer(transaction);

        // Then
        StepVerifier.create(result)
                .expectError(IllegalArgumentException.class)
                .verify();
    }
}