package com.example.socialnetworkingapp.model.account.details;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccountDetails {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String imageUrl;
    private String dateCreated;
}