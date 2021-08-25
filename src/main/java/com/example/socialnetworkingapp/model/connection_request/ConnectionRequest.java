package com.example.socialnetworkingapp.model.connection_request;

import com.example.socialnetworkingapp.account.Account;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name="connx_requests")
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionRequest {

    private enum RequestStatus {
        PENDING,
        ACCEPTED,
        REJECTED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id ;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sender_id", nullable = false)
    private Account sender;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "receiver_id", nullable = false)
    private Account receiver;

    @NotNull
    private RequestStatus requestStatus = RequestStatus.PENDING;
}