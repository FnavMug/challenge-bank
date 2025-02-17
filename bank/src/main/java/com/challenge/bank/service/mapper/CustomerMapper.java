package com.challenge.bank.service.mapper;

import com.challenge.bank.repository.entity.CustomerEntity;
import com.challenge.bank.controller.request.CustomerRequest;
import com.challenge.bank.service.model.Customer;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    CustomerRequest toDto(Customer model);

    Customer toModel(CustomerRequest dto);

    Customer toModel(CustomerEntity entity);

    CustomerEntity toEntity(Customer model);

}
