package com.henry.World.Banking.Application.service.Impl;

import com.henry.World.Banking.Application.domain.entity.UserEntity;
import com.henry.World.Banking.Application.payload.request.CreditAndDebitRequest;
import com.henry.World.Banking.Application.payload.request.EmailDetails;
import com.henry.World.Banking.Application.payload.request.EnquiryRequest;
import com.henry.World.Banking.Application.payload.response.AccountInfo;
import com.henry.World.Banking.Application.payload.response.BankResponse;
import com.henry.World.Banking.Application.repository.UserRepository;
import com.henry.World.Banking.Application.service.EmailService;
import com.henry.World.Banking.Application.service.UserService;
import com.henry.World.Banking.Application.utils.AccountUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

import static com.henry.World.Banking.Application.utils.AccountUtils.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private EmailService emailService;

    /**
     * @param enquiryRequest
     * @return
     */
    @Override
    public BankResponse balanceEnquiry(EnquiryRequest enquiryRequest) {
        boolean isAccountExits =
                userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());

        if(!isAccountExits){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NUMBER_NON_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NUMBER_NON_EXISTS_MESSAGE)
                    .build();
        }

        UserEntity foundUserAccount =
                userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());


        return BankResponse.builder()
                .responseCode(ACCOUNT_EXISTS_CODE)
                .responseMessage(ACCOUNT_NUMBER_FOUND_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(foundUserAccount.getAccountBalance())
                        .accountNumber(enquiryRequest.getAccountNumber())
                        .accountName(foundUserAccount.getFirstName() + " " + foundUserAccount.getLastName())
                        .build())
                .build();
    }

    /**
     * @param enquiryRequest
     * @return
     */
    @Override
    public String nameEnquiry(EnquiryRequest enquiryRequest){

        boolean isAccountExits =
                userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());

        if (!isAccountExits) {
            return ACCOUNT_NUMBER_NON_EXISTS_MESSAGE;
        }

        UserEntity foundUserAccount =
                userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());


        return foundUserAccount.getFirstName() + " " + foundUserAccount.getLastName() +
                " " + foundUserAccount.getOtherName();
    }

    @Override
    public BankResponse creditAccount(CreditAndDebitRequest request) {
        boolean isAccountExists = userRepository.existsByAccountNumber(request.getAccountNumber());

        if (!isAccountExists) {
            return BankResponse.builder()
                    .responseCode(ACCOUNT_NUMBER_NON_EXISTS_CODE)
                    .responseMessage(ACCOUNT_NUMBER_NON_EXISTS_MESSAGE)
                    .build();
        }

        UserEntity userToCredit = userRepository.findByAccountNumber(request.getAccountNumber());

        userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(request.getAmount()));

        // userToCredit.setFirstName(request.getFirstName());
        // userToCredit.setLastName(request.getLastName());

        userRepository.save(userToCredit);

        EmailDetails creditAlert = EmailDetails.builder()
                .subject("CREDIT ALERT NOTIFICATION")
                .recipient(userToCredit.getEmail())
                .messageBody("Your account has been credited with " + request.getAmount() +
                        " from" + userToCredit.getFirstName() +
                        "your current account balance is " +
                        userToCredit.getAccountBalance())
                .build();

        emailService.sendEmailAlert(creditAlert);

        return BankResponse.builder()
                .responseCode(ACCOUNT_CREATION_SUCCESS_CODE)
                .responseMessage(ACCOUNT_CREATION_SUCCESS_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountName(userToCredit.getFirstName() + " " + userToCredit.getOtherName() + " " + userToCredit.getLastName())
                        .accountBalance(userToCredit.getAccountBalance())
                        .accountNumber(userToCredit.getAccountNumber())
                        .build())
                .build();

    }

    /**
     * @param request
     * @return
     */
    @Override
    public BankResponse debitAccount(CreditAndDebitRequest request) {
        boolean isAccountExists = userRepository.existsByAccountNumber(request.getAccountNumber());

        if(!isAccountExists){
            return BankResponse.builder()
                    .responseCode(ACCOUNT_NUMBER_NON_EXISTS_CODE)
                    .responseMessage(ACCOUNT_NUMBER_NON_EXISTS_MESSAGE)
                    .build();
        }

        UserEntity userToDebit = userRepository.findByAccountNumber(request.getAccountNumber());

        //check for insufficient balance

        BigInteger availableBalance = userToDebit.getAccountBalance().toBigInteger();
        BigInteger debitAmount = request.getAmount().toBigInteger();


        if(availableBalance.intValue() < debitAmount.intValue()){
            return  BankResponse.builder()
                    .responseCode(ACCOUNT_DEBITED_FAILURE_CODE)
                    .responseMessage(ACCOUNT_CREDIT_SUCCESS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }else {
            userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(request.getAmount()));

            userRepository.save(userToDebit);

            EmailDetails debitAlert = EmailDetails.builder()
                    .subject("DEBIT ALERT NOTIFICATION")
                    .recipient(userToDebit.getEmail())
                    .messageBody("The sum of " + request.getAmount() +
                            "has been debited from your account! Your current balance is " +
                            userToDebit.getAccountBalance())
                    .build();

            emailService.sendEmailAlert(debitAlert);
        }


        return BankResponse.builder()
                .responseCode(ACCOUNT_DEBITED_SUCCESS_CODE)
                .responseMessage(ACCOUNT_DEBIT_SUCCESS_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountName(userToDebit.getFirstName() + userToDebit.getOtherName() + userToDebit.getLastName())
                        .accountBalance(userToDebit.getAccountBalance())
                        .accountNumber(userToDebit.getAccountNumber())
                        .bankName("WORLD BANK APP")
                        .build())
                .build();

    }
}
