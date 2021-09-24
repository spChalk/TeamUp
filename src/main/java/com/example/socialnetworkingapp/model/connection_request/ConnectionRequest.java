package com.example.socialnetworkingapp.model.connection_request;

import com.example.socialnetworkingapp.model.account.Account;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id ;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Account sender;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Account receiver;

    public ConnectionRequest(Account sender, Account receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }
}