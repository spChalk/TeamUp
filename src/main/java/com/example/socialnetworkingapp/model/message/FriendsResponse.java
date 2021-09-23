package com.example.socialnetworkingapp.model.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
@Builder
public class FriendsResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String imageUrl;
}
