package com.example.socialnetworkingapp.model.message;
import com.example.socialnetworkingapp.model.account.Account;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "messages")
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id ;

    private String payload;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Account sender;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Account receiver;

    private LocalDateTime date;

    public Message(String payload, Account sender, Account receiver, LocalDateTime date) {
        this.payload = payload;
        this.sender = sender;
        this.receiver = receiver;
        this.date = date;
    }
}
