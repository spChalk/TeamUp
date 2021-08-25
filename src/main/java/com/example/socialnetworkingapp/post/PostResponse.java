package com.example.socialnetworkingapp.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class PostResponse {
    private String title;
    private String payload;
    private String authorFirstName;
    private String authorLastName;
    private String authorEmail;
    private Date date;

}
