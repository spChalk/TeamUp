package com.example.socialnetworkingapp.model.message;

import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.account.AccountService;
import com.example.socialnetworkingapp.model.message.Message;
import com.example.socialnetworkingapp.model.message.Message;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/messages")
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final AccountService accountService;

    @PostMapping("/chat")
    public List<MessageResponse> getConversation(@RequestBody String receiver){
        Account account = this.accountService.findAccountByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Account friend = this.accountService.findAccountByEmail(receiver);
        return this.messageService.getConversation(account, friend);
    }

    @GetMapping("/friends")
    public Set<FriendsResponse> getMessages() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        Account account = this.accountService.findAccountByEmail(user);
        return this.messageService.getFriends(account);
    }

    @PostMapping("/send")
    public ResponseEntity<Message> addMessage(@RequestBody MessageRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String senderMail = authentication.getName();
        Account sender = accountService.findAccountByEmail(senderMail);
        Account receiver = accountService.findAccountByEmail(request.getReceiverMail());
        Message newMessage = new Message(request.getPayload(), sender, receiver, LocalDateTime.now());
        return new ResponseEntity<>( messageService.addMessage(newMessage) , HttpStatus.CREATED);
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