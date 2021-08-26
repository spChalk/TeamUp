package com.example.socialnetworkingapp.model.like;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class LikeResponse {
    private String title;
    private String payload;
    private String authorFirstName;
    private String authorLastName;
    private String authorEmail;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private Date date;
}