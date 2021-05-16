package com.SalarJavaDevGroup.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.DateTimeException;
import java.time.LocalDate;

public abstract class Validate {
    private static final Logger logger = LogManager.getLogger(Validate.class);



    // checking if the date exists
    public static boolean validDate(String date) {
        try {
            LocalDate.parse(date);
            return true;
        }catch (DateTimeException dateTimeException) {
            return false;
        }
    }
    // checking if email is valid type
    public static boolean validEmail(String Email) {
        int i = 0;
        while (i < Email.length()) {
            if (Email.charAt(i) == '@')
                break;
            i++;
        }
        while (i < Email.length()) {
            if (Email.charAt(i) == '.')
                break;
            i++;
        }
        return i != Email.length();
    }
    //checking if the phone is valid
    public static boolean validPhone(String PhoneNumber) {
        return PhoneNumber.length() == 11 && PhoneNumber.charAt(0) == '0';
    }



}
