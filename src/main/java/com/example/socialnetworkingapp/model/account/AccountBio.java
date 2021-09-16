package com.example.socialnetworkingapp.model.account;

import com.example.socialnetworkingapp.model.bio.Bio;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AccountBio {

    private String email;
    private Bio bio;
}