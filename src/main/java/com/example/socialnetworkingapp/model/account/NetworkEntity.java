package com.example.socialnetworkingapp.model.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
public class NetworkEntity implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private String imageUrl;
}