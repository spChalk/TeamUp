package com.example.socialnetworkingapp.model.account;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountUpdateRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
}
