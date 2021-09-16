package com.example.socialnetworkingapp.model.account;

import com.example.socialnetworkingapp.model.experience.Experience;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AccountExperience {

    private String email;
    private Experience xp;
}