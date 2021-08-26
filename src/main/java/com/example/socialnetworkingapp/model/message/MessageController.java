package com.example.socialnetworkingapp.model.message;

import com.example.socialnetworkingapp.model.message.Message;
import com.example.socialnetworkingapp.model.message.Message;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/chat")
    public List<Message> getConversation(
            @RequestParam(required = true) Long sender_id,
            @RequestParam(required = true) Long receiver_id) {
        return this.messageService.getConversation(sender_id, receiver_id);
    }

    @PostMapping("/add")
    public ResponseEntity<Message> addMessage(@RequestBody Message message){
        Message newMessage = messageService.addMessage(message);
        return new ResponseEntity<>(newMessage, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Message> updateMessage(@RequestBody Message message){
        Message newMessage = messageService.updateMessage(message);
        return new ResponseEntity<>(newMessage, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMessageById(@PathVariable("id") Long id){
        messageService.deleteMessage(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}