package com.example.socialnetworkingapp.model.connection_request;

import com.example.socialnetworkingapp.exception.UserNotFoundException;
import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.account.AccountService;
import lombok.AllArgsConstructor;
import net.bytebuddy.matcher.CollectionOneToOneMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConnectionReqService {

    private final ConnectionReqRepository connectionReqRepository;
    private final AccountService accountService;

    public List<ConnectionRequest> findRequestsByAccId(Long id) {
        return this.connectionReqRepository.findRequestsByAccId(id).
                orElseThrow( () -> new UserNotFoundException("User by id "+ id + "was not found !"));
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

    public ConnectionRequest addRequest(ConnectionRequest connectionRequest) {
        Optional<ConnectionRequest> alreadyExists = this.connectionReqRepository
                .findRequestByAccIds(connectionRequest.getSender().getId(),
                        connectionRequest.getReceiver().getId());
        return alreadyExists.orElseGet(() -> this.connectionReqRepository.save(connectionRequest));
    }

    public void deleteRequest(Long id) {
        this.connectionReqRepository.deleteById(id);
    }

    public void acceptRequest(ConnectionRequest connectionRequest) {

        this.connectionReqRepository.delete(connectionRequest);
        this.accountService.connect(connectionRequest.getSender().getEmail(), connectionRequest.getReceiver().getEmail());
    }

    public void rejectRequest(ConnectionRequest connectionRequest) {
        this.connectionReqRepository.delete(connectionRequest);
    }

    public void deleteConnection(String usr1, String usr2) {

        this.accountService.removeConnection(usr1, usr2);
    }
}