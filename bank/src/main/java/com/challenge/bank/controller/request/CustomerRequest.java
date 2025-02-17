package com.challenge.bank.controller.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class CustomerRequest {

    @NonNull
    private String name;
    @NonNull
    private String email;
    @NonNull
    private String phoneNumber;
    @NonNull
    private String doc;

}
