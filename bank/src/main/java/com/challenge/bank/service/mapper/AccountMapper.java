package com.challenge.bank.service.mapper;

import com.challenge.bank.repository.entity.AccountEntity;
import com.challenge.bank.controller.request.AccountRequest;
import com.challenge.bank.controller.response.AccountResponse;
import com.challenge.bank.service.model.Account;
import org.mapstruct.Mapper;

@Mapper
public interface AccountMapper {

    Account toModel(AccountRequest dto);

    Account toModel(AccountEntity entity);

    AccountEntity toEntity(Account model);

    AccountResponse toResponse(Account model);
}
