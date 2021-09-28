package com.example.socialnetworkingapp.model.message;

import com.example.socialnetworkingapp.exception.UserNotFoundException;
import com.example.socialnetworkingapp.mapper.FriendMapper;
import com.example.socialnetworkingapp.mapper.MessageMapper;
import com.example.socialnetworkingapp.model.account.Account;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final FriendMapper friendMapper;

    public List<MessageResponse> getConversation(Account me, Account friend) {

        List<Message> messages = this.messageRepository.getConversation(me , friend).
                orElseThrow(() -> new UserNotFoundException("One or more users not found!"));

        return messages.stream().map(messageMapper::MessageToMessageResponse).collect(Collectors.toList());
    }

    public Set<FriendsResponse> getFriends(Account account) {
        Set<Account> friends = this.messageRepository.getSentMessages(account).
                orElseThrow(() -> new UserNotFoundException("One or more users not found!"));

        Set<Account> friends1 = this.messageRepository.getReceivedMessages(account).
                orElseThrow(() -> new UserNotFoundException("One or more users not found!"));

        Set<FriendsResponse> temp1 = friends.stream().map(friendMapper::AccountToFriend).collect(Collectors.toSet());
        Set<FriendsResponse> temp2 = friends1.stream().map(friendMapper::AccountToFriend).collect(Collectors.toSet());

        temp1.addAll(temp2);
        return temp1;
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