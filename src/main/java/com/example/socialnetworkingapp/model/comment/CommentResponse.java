package com.example.socialnetworkingapp.model.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class CommentResponse {
    private Long id;
    private String payload;
    private String authorFirstName;
    private String authorLastName;
    private String authorEmail;
    private String authorImage;
    private String date;
}
