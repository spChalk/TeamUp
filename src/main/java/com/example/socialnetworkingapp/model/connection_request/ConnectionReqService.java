package com.example.socialnetworkingapp.model.connection_request;

import com.example.socialnetworkingapp.exception.UserNotFoundException;
import com.example.socialnetworkingapp.mapper.ConnectionRequestMapper;
import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.account.AccountService;
import lombok.AllArgsConstructor;
import net.bytebuddy.matcher.CollectionOneToOneMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ConnectionReqService {

    private final ConnectionReqRepository connectionReqRepository;
    private final AccountService accountService;
    private final ConnectionRequestMapper connectionRequestMapper;

    public List<ConnectionRequestResponse> findRequestsByAccId(Long id) {

        List<ConnectionRequest> connReqs = this.connectionReqRepository.findRequestsByAccId(id).
                orElseThrow( () -> new UserNotFoundException("User by id "+ id + "was not found !"));
        return connReqs.stream().map(connectionRequestMapper::ConnectionRequestToConnectionRequestResponse).collect(Collectors.toList());
    }

    public List<ConnectionRequestResponse> findAllRequestsByAccId(Long id) {

        List<ConnectionRequest> connReqs = this.connectionReqRepository.findAllRequestsByAccId(id).
                orElseThrow( () -> new UserNotFoundException("User by id "+ id + "was not found !"));
        return connReqs.stream().map(connectionRequestMapper::ConnectionRequestToConnectionRequestResponse).collect(Collectors.toList());
    }

    public Long findRequestByAccEmails(String senderEmail, String receiverEmail) {

        Long senderId = this.accountService.findAccountByEmail(senderEmail).getId();
        Long receiverId = this.accountService.findAccountByEmail(receiverEmail).getId();
        Optional<ConnectionRequest> exists = this.connectionReqRepository.findRequestByAccIds(senderId, receiverId);
        return exists.map(ConnectionRequest::getId).orElse(null);
    }

/*    public ConnectionRequest findAcceptedRequestByEmails(String email1, String email2) {
        return this.connectionReqRepository.findAcceptedRequestsByEmails(email1, email2);
    }*/

/*    public List<ConnectionRequest> findReceivedAcceptedRequestsByAccId(Long id) {
        return this.connectionReqRepository.findReceivedAcceptedRequestsByAccId(id).
                orElseThrow( () -> new UserNotFoundException("User by id "+ id + "was not found !"));
    }

    public List<ConnectionRequest> findSentAcceptedRequestsByAccId(Long id) {
        return this.connectionReqRepository.findSentAcceptedRequestsByAccId(id).
                orElseThrow( () -> new UserNotFoundException("User by id "+ id + "was not found !"));
    }

    public List<ConnectionRequest> findReceivedAcceptedRequestsByAccEmail(String email) {
        return this.connectionReqRepository.findReceivedAcceptedRequestsByAccEmail(email).
                orElseThrow( () -> new UserNotFoundException("User with email "+ email + "was not found !"));
    }

    public List<ConnectionRequest> findSentAcceptedRequestsByAccEmail(String email) {
        return this.connectionReqRepository.findSentAcceptedRequestsByAccEmail(email).
                orElseThrow( () -> new UserNotFoundException("User with email "+ email + "was not found !"));
    }*/

    public ConnectionRequestResponse addRequest(ConnectionRequest connectionRequest) {
        Optional<ConnectionRequest> alreadyExists = this.connectionReqRepository
                .findRequestByAccIds(connectionRequest.getSender().getId(),
                        connectionRequest.getReceiver().getId());
        List<ConnectionRequest> newReq = new ArrayList<>();
        if(alreadyExists.isPresent()) {
            newReq.add(alreadyExists.get());
        } else {
            newReq.add(this.connectionReqRepository.save(connectionRequest));
        }
        return newReq.stream().map(connectionRequestMapper::ConnectionRequestToConnectionRequestResponse).collect(Collectors.toList()).get(0);
    }

    public void deleteRequest(Long id) {
        this.connectionReqRepository.deleteById(id);
    }

    public void acceptRequest(Account myAcc, Account otherAcc) {
        Optional<ConnectionRequest> alreadyExists = this.connectionReqRepository
                .findRequestByAccIds(otherAcc.getId(), myAcc.getId());
        if(alreadyExists.isPresent()) {
            this.connectionReqRepository.delete(alreadyExists.get());
            this.accountService.connect(myAcc.getEmail(), otherAcc.getEmail());
        }
    }

    public void rejectRequest(Account myAcc, Account otherAcc) {
        Optional<ConnectionRequest> alreadyExists = this.connectionReqRepository
                .findRequestByAccIds(otherAcc.getId(), myAcc.getId());
        alreadyExists.ifPresent(this.connectionReqRepository::delete);
    }

    public void deleteConnection(String usr1, String usr2) {
        this.accountService.removeConnection(usr1, usr2);
    }
}