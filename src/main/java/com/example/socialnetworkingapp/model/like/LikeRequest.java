package com.example.socialnetworkingapp.model.like;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LikeRequest {
    private String title;
    private String payload;
}