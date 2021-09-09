package com.example.socialnetworkingapp.model.connection_request;

import com.example.socialnetworkingapp.exception.UserNotFoundException;
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

    public List<ConnectionRequest> findPendingRequestsByAccId(Long id) {
        return this.connectionReqRepository.findPendingRequestsByAccId(id).
                orElseThrow( () -> new UserNotFoundException("User by id "+ id + "was not found !"));
    }

    public List<ConnectionRequest> findReceivedAcceptedRequestsByAccId(Long id) {
        return this.connectionReqRepository.findReceivedAcceptedRequestsByAccId(id).
                orElseThrow( () -> new UserNotFoundException("User by id "+ id + "was not found !"));
    }

    public List<ConnectionRequest> findSentAcceptedRequestsByAccId(Long id) {
        return this.connectionReqRepository.findSentAcceptedRequestsByAccId(id).
                orElseThrow( () -> new UserNotFoundException("User by id "+ id + "was not found !"));
    }

    public ConnectionRequest addRequest(ConnectionRequest connectionRequest) {
        Optional<ConnectionRequest> alreadyExists = this.connectionReqRepository
                .findPendingRequestByAccIds(connectionRequest.getSender().getId(),
                        connectionRequest.getReceiver().getId());
        return alreadyExists.orElseGet(() -> this.connectionReqRepository.save(connectionRequest));
    }

    public void deleteRequest(Long id) {
        this.connectionReqRepository.deleteById(id);
    }

    public void acceptRequest(ConnectionRequest connectionRequest) {
        this.connectionReqRepository.acceptRequest(connectionRequest.getId());
    }

    public void rejectRequest(ConnectionRequest connectionRequest) {
        this.connectionReqRepository.rejectRequest(connectionRequest.getId());
    }

    public void deleteRequestByAccIds(Long me, Long uid) {
        this.connectionReqRepository.deleteByAccIds(me, uid);
    }
}