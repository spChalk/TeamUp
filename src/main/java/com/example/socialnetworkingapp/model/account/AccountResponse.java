package com.example.socialnetworkingapp.model.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class AccountResponse {

    private Long id;
    private AppUserRole role;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private List<String> following ;
}
