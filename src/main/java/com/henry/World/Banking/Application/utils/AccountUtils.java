package com.henry.World.Banking.Application.utils;

import java.time.Year;

public class AccountUtils {

    public static final String ACCOUNT_EXISTS_CODE = "001";
    public static final String ACCOUNT_EXISTS_MESSAGE =
            "This user already have an account created!";
    public static final String ACCOUNT_CREATION_SUCCESS_CODE = "002";
    public static final  String ACCOUNT_CREATION_SUCCESS_MESSAGE =
            "Account has been created Successfully!";


    public static final String ACCOUNT_NUMBER_NON_EXISTS_CODE = "003";


    public static final String ACCOUNT_NUMBER_NON_EXISTS_MESSAGE =
            "Provide account number does not exists!";

    public static final String ACCOUNT_NUMBER_FOUND_CODE = "004";

    public static final String ACCOUNT_NUMBER_FOUND_MESSAGE =
            "Account number found";

    public static final String ACCOUNT_CREDIT_SUCCESS_CODE = "005";

    public static final String ACCOUNT_CREDIT_SUCCESS_MESSAGE =
            "Account credited successfully";

    public static final String ACCOUNT_DEBITED_SUCCESS_CODE = "006";

    public static final String ACCOUNT_DEBIT_SUCCESS_MESSAGE =
            "Account debited successfully";

    public static final String ACCOUNT_DEBITED_FAILURE_CODE = "007";

    public static final String ACCOUNT_DEBITED_FAILURE_MESSAGE =
            "Your account is too low to make this debit";





    public static String generateAccountNumber(){
        /**
         * This algorithm will assume that account number will be total
         * of 10 digits, since we basically have 10 account numbers in
         * Nigeria
         */

        //1.Get the current year
        Year currentYear = Year.now();

        //2. Get 6 random digits
        int min = 100000;
        int max = 999999;

        // generate a random number between min and max
        int randomNumber = (int)Math.floor(Math.random() * (max - min + 1) + min);

        //convert current year and random six number to string and then concatenate them
        String year = String.valueOf(currentYear);
        String randomNum = String.valueOf(randomNumber);

        //append both year and random num to generate the 10 digit account number
        StringBuilder accountNumber = new StringBuilder();

        return accountNumber.append(year).append(randomNum).toString();
    }


}
