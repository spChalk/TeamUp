package com.example.socialnetworkingapp.model.connection_request;

import com.example.socialnetworkingapp.model.account.Account;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "connection_request")
public class ConnectionRequest implements Serializable {

    public enum RequestStatus {
        PENDING,
        ACCEPTED,
        REJECTED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id ;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(nullable = false)
    private Account sender;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(nullable = false)
    private Account receiver;

    @NotNull
    private RequestStatus requestStatus = RequestStatus.PENDING;

    public ConnectionRequest(Account sender, Account receiver, RequestStatus requestStatus) {
        this.sender = sender;
        this.receiver = receiver;
        this.requestStatus = requestStatus;
    }

    public ConnectionRequest(Account sender, Account receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }
}