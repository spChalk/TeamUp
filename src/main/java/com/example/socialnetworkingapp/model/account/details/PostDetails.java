package com.example.socialnetworkingapp.model.account.details;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
public class PostDetails {

    private String payload;
    private String date;
    private String imagePath;
    private String videoPath;
    private String soundPath;
}