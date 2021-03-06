package com.example.socialnetworkingapp.model.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MessageResponse {
    private String payload;
    private String date;

    private String senderEmail;
    private String senderFirstName;
    private String senderLastName;
    private String senderImageUrl;
    private String senderPhone;

    private String receiverEmail;
    private String receiverFirstName;
    private String receiverLastName;
    private String receiverImageUrl;
    private String receiverPhone;
}