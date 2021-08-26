package com.example.socialnetworkingapp.model.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MessageResponse {
    private String payload;
    private String senderFirstName;
    private String senderLastName;
    private String senderEmail;
    private String receiverFirstName;
    private String receiverLastName;
    private String receiverEmail;
}