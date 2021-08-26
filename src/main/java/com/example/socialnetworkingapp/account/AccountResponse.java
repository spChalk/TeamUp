package com.example.socialnetworkingapp.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
