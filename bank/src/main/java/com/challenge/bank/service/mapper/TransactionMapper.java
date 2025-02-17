package com.challenge.bank.service.mapper;

import com.challenge.bank.controller.response.TransactionResponse;
import com.challenge.bank.service.model.Transaction;
import com.challenge.bank.repository.entity.TransactionEntity;
import org.mapstruct.Mapper;

@Mapper
public interface TransactionMapper {

    TransactionResponse toResponse(Transaction model);

    Transaction toModel(TransactionEntity entity);

    TransactionEntity toEntity(Transaction model);

}