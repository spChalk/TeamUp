package com.example.socialnetworkingapp.model.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Default;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class PostResponse {
    private Long id;
    private String payload;
    private String authorFirstName;
    private String authorLastName;
    private String authorEmail;
    private String authorImage;
    private String date;
    private String imagePath;
    private String videoPath;
    private String soundPath;
}
