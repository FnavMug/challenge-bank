package com.challenge.bank.domain.mapper;

import com.challenge.bank.adapter.entity.AccountEntity;
import com.challenge.bank.application.controller.request.AccountRequest;
import com.challenge.bank.application.controller.response.AccountResponse;
import com.challenge.bank.domain.model.Account;
import org.mapstruct.Mapper;

@Mapper
public interface AccountMapper {

    Account toModel(AccountRequest dto);

    Account toModel(AccountEntity entity);

    AccountEntity toEntity(Account model);

    AccountResponse toResponse(Account model);
}
