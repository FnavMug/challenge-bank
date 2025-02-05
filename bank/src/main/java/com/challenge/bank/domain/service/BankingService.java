package com.challenge.bank.domain.service;

import com.challenge.bank.adapter.repository.AccountRepository;
import com.challenge.bank.adapter.repository.CustomerRepository;
import com.challenge.bank.adapter.repository.TransactionRepository;
import com.challenge.bank.application.exceptions.EntityNotFoundException;
import com.challenge.bank.domain.mapper.CustomerMapper;
import com.challenge.bank.domain.mapper.AccountMapper;
import com.challenge.bank.domain.mapper.TransactionMapper;
import com.challenge.bank.domain.model.Account;
import com.challenge.bank.domain.model.Customer;
import com.challenge.bank.domain.model.Transaction;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * This service is responsible for the CRUD operations of the banking system
 */
@Service
public class BankingService {

    Logger logger = LoggerFactory.getLogger(BankingService.class);

    private static final CustomerMapper CustomerMapper = Mappers.getMapper(CustomerMapper.class);
    private static final AccountMapper AccountMapper = Mappers.getMapper(AccountMapper.class);
    private static final TransactionMapper TransactionMapper = Mappers.getMapper(TransactionMapper.class);

    /**
     * CRUD Repositories aren't created/implemented using resilient patterns, because the repositories have resiliency built-in.
     */
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;

    public BankingService(AccountRepository accountRepository, TransactionRepository transactionRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository;
    }

    public void createCustomer(Customer customer) {
        logger.info("Creating customer: {}", customer.getCustomerId());
        customerRepository.save(CustomerMapper.toEntity(customer));
    }

    public void createAccount(Account account) {
        logger.info("Creating account: {}", account);
        accountRepository.save(AccountMapper.toEntity(account));

    }

    /**
     * Deposit money into an account, if the account is not found, throws an EntityNotFoundException
     * flatmap is used to chain the operations of updating the account balance and saving the transaction
     * so it's not blocking
     * @param transaction the transaction to be deposited
     * @return a mono of the transaction
     */
    public Mono<Transaction> deposit(Transaction transaction) {
        logger.info("Depositing: {}", transaction);

        return accountRepository.findById(transaction.getAccountId())
                .map(AccountMapper::toModel)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Account not found")))
                .flatMap(account -> {
                    account.setBalance(account.getBalance() + transaction.getAmount());
                    return accountRepository.save(AccountMapper.toEntity(account))
                            .then(transactionRepository.save(TransactionMapper.toEntity(transaction)))
                            .map(TransactionMapper::toModel);
                });
    }

    /**
     * Withdraw money from an account, if the account is not found, throws an EntityNotFoundException
     *
     * @param transaction the transaction to be withdrawn
     * @return a mono of the transaction
     */
    public Mono<Transaction> withdraw(Transaction transaction) {
        logger.info("Withdrawing: {}", transaction);

        return accountRepository.findById(transaction.getAccountId())
                .map(AccountMapper::toModel)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Account not found")))
                .flatMap(account -> {
                    if (account.getBalance() < transaction.getAmount()) {
                        return Mono.error(new IllegalArgumentException("Insufficient balance"));
                    }
                    account.setBalance(account.getBalance() - transaction.getAmount());
                    return accountRepository.save(AccountMapper.toEntity(account))
                            .then(transactionRepository.save(TransactionMapper.toEntity(transaction)))
                            .map(TransactionMapper::toModel);
                });
    }

    /**
     * Transfer money from one account to another, if either account is not found, throws an EntityNotFoundException
     * if the account doesn't have enough balance, throws an IllegalArgumentException
     * then it saves both accounts and the transaction
     * @param transaction the transaction to be transferred
     * @return a mono of the transaction
     */
    public Mono<Transaction> transfer(Transaction transaction) {
        logger.info("Transferring: {}", transaction);

        return accountRepository.findById(transaction.getAccountId())
                .map(AccountMapper::toModel)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Account not found")))
                .flatMap(accountFrom ->
                        accountRepository.findById(transaction.getRecipientAccountId())
                                .map(AccountMapper::toModel)
                                .switchIfEmpty(Mono.error(new EntityNotFoundException("Recipient account not found")))
                                .flatMap(accountTo -> {
                                    if (accountFrom.getBalance() < transaction.getAmount()) {
                                        return Mono.error(new IllegalArgumentException("Insufficient balance"));
                                    }
                                    accountFrom.setBalance(accountFrom.getBalance() - transaction.getAmount());
                                    accountTo.setBalance(accountTo.getBalance() + transaction.getAmount());
                                    return accountRepository.save(AccountMapper.toEntity(accountFrom))
                                            .then(accountRepository.save(AccountMapper.toEntity(accountTo)))
                                            .then(transactionRepository.save(TransactionMapper.toEntity(transaction)))
                                            .map(TransactionMapper::toModel);
                                })
                );
    }

    /**
     * Get the balance of an account, if the account is not found, throws an EntityNotFoundException
     *
     * @param account the account to get the balance for
     * @return a mono of the account
     */
    public Mono<Account> getBalance(Account account) {
        logger.info("Getting balance: {}", account);

        return accountRepository.findById(account.getAccountId())
                .map(AccountMapper::toModel)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Account not found")));

    }

    /**
     * Get all transactions by account id, flux is used to handle multiple transactions asynchronously
     * @param accountId the account id to get transactions for
     * @return a flux of transactions for the account id
     */
    public Flux<Transaction> getTransactionsByAccountId(UUID accountId) {
        return transactionRepository.findByAccountId(accountId)
                .map(TransactionMapper::toModel);
    }

    /**
     * Get all transactions by recipient account id, flux is used to handle multiple transactions asynchronously
     * @param recipientAccountId the recipient account id to get transactions for
     * @return a flux of transactions for the recipient account id
     */
    public Flux<Transaction> getTransactionsByRecipientAccountId(UUID recipientAccountId) {
        return transactionRepository.findByRecipientAccountId(recipientAccountId)
                .map(TransactionMapper::toModel);
    }


}
