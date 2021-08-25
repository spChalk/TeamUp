package com.example.socialnetworkingapp.model.connection_request;

import com.example.socialnetworkingapp.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import net.bytebuddy.matcher.CollectionOneToOneMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ConnectionReqService {

    private final ConnectionReqRepository connectionReqRepository;

    public List<ConnectionRequest> findRequestsByAccId(Long id) {
        return this.connectionReqRepository.findRequestsByAccId(id).
                orElseThrow( () -> new UserNotFoundException("User by id "+ id + "was not found !"));
    }

    public ConnectionRequest addRequest(ConnectionRequest connectionRequest) {
        return this.connectionReqRepository.save(connectionRequest);
    }

    public void deleteRequest(Long id) {
        this.connectionReqRepository.deleteById(id);
    }
}