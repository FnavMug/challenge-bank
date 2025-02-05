package com.challenge.bank.adapter.repository;

import com.challenge.bank.adapter.entity.AccountEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends ReactiveCrudRepository<AccountEntity, UUID> {

    List<AccountEntity> findByCustomerId(UUID customerId);

}
