package com.example.socialnetworkingapp.model.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Friends {
    private String senderEmail;
    private String receiverEmail;
}
