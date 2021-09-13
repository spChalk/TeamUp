package com.example.socialnetworkingapp.model.interests.account_tags;

import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.interests.Tags;
import com.sun.istack.NotNull;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "account_tags")
public class AccountInterest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;

    @NotNull
    @ManyToOne
    private Account account;

    @NotNull
    private Tags tag;
}