package com.example.socialnetworkingapp.model.message;

import com.example.socialnetworkingapp.exception.UserNotFoundException;
import com.example.socialnetworkingapp.model.account.Account;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public List<Message> getConversation(Long sender_id, Long receiver_id) {
        return this.messageRepository.getConversation(sender_id, receiver_id).
                orElseThrow(() -> new UserNotFoundException("One or more users not found!"));
    }

    public Message addMessage(Message message) {
        return this.messageRepository.save(message);
    }

    public Message updateMessage(Message message) {
        return this.messageRepository.save(message);
    }

    public void deleteMessage(Long id) {
        this.messageRepository.deleteById(id);
    }
}