package com.example.socialnetworkingapp.model.interests;

import com.example.socialnetworkingapp.account.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "interests")
public class Interest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id ;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name ="account_id", nullable = false)
    private Set<Account> users;
}