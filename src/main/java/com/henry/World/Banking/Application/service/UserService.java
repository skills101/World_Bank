package com.henry.World.Banking.Application.service;

import com.henry.World.Banking.Application.payload.request.CreditAndDebitRequest;
import com.henry.World.Banking.Application.payload.request.EnquiryRequest;
import com.henry.World.Banking.Application.payload.response.BankResponse;

public interface UserService {

    BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);


    String nameEnquiry (EnquiryRequest enquiryRequest);

    BankResponse creditAccount(CreditAndDebitRequest request);

    BankResponse debitAccount(CreditAndDebitRequest request);
}
