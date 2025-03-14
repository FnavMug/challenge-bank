package com.challenge.bank.repository.repository;

import com.challenge.bank.repository.entity.CustomerEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<CustomerEntity, UUID> {

    List<CustomerEntity> findByDoc(String doc);

}
