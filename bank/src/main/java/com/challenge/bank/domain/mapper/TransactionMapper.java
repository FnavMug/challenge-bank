package com.challenge.bank.domain.mapper;

import com.challenge.bank.application.controller.response.TransactionResponse;
import com.challenge.bank.domain.model.Transaction;
import com.challenge.bank.adapter.entity.TransactionEntity;
import org.mapstruct.Mapper;

@Mapper
public interface TransactionMapper {

    TransactionResponse toResponse(Transaction model);

    Transaction toModel(TransactionEntity entity);

    TransactionEntity toEntity(Transaction model);

}