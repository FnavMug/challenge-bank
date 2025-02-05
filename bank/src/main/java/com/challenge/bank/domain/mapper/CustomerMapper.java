package com.challenge.bank.domain.mapper;

import com.challenge.bank.adapter.entity.CustomerEntity;
import com.challenge.bank.application.controller.request.CustomerRequest;
import com.challenge.bank.domain.model.Customer;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    CustomerRequest toDto(Customer model);

    Customer toModel(CustomerRequest dto);

    Customer toModel(CustomerEntity entity);

    CustomerEntity toEntity(Customer model);

}
