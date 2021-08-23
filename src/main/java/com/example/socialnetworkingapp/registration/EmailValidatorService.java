package com.example.socialnetworkingapp.registration;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class EmailValidatorService implements Predicate<String> {
    @Override
    public boolean test(String s) {
        //TODO : regex to validate email, for example chris@something.com is valid but example.com is not a valid mail
        return true;
    }
}
