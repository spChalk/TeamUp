package com.example.socialnetworkingapp.model.like;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class LikeResponse {
    private Long id;
    private String userFirstName;
    private String userLastName;
}