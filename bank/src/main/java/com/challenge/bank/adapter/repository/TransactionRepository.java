package com.challenge.bank.adapter.repository;

import com.challenge.bank.adapter.entity.TransactionEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface TransactionRepository extends ReactiveCrudRepository<TransactionEntity, UUID> {

    Flux<TransactionEntity> findByAccountId(UUID accountId);

    Flux<TransactionEntity> findByRecipientAccountId(UUID recipientAccountId);

}
