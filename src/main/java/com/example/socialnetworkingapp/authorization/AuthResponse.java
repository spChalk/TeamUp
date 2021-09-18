package com.example.socialnetworkingapp.authorization;

import com.example.socialnetworkingapp.model.account.AccountRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private String username;
    private String token;
    private AccountRole role;
}
