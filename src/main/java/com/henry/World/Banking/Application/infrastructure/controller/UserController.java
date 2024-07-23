package com.henry.World.Banking.Application.infrastructure.controller;


import com.henry.World.Banking.Application.payload.request.CreditAndDebitRequest;
import com.henry.World.Banking.Application.payload.request.EnquiryRequest;
import com.henry.World.Banking.Application.payload.response.BankResponse;
import com.henry.World.Banking.Application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/balance-enquiry")
    public BankResponse balanceEnquiry(@RequestBody EnquiryRequest enquiryRequest){
        return userService.balanceEnquiry(enquiryRequest);

    }

    @GetMapping("/name-enquiry")
    public String nameEnquiry( @RequestBody EnquiryRequest enquiryRequest){
        return userService.nameEnquiry(enquiryRequest);
    }

    @PostMapping("/credit-account")
    public BankResponse creditAccount(@RequestBody CreditAndDebitRequest request){
        return userService.creditAccount(request);
    }

    @PostMapping("/debit-account")
    public BankResponse debitAccount(@RequestBody CreditAndDebitRequest request){
        return userService.debitAccount(request);
    }
}
