package com.example.socialnetworkingapp.model.message;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;


}