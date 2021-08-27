package com.example.socialnetworkingapp.model.ads;

import com.example.socialnetworkingapp.model.account.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="ads")
@AllArgsConstructor
@NoArgsConstructor
public class Advertisement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id ;

    private String title;
    private String payload;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account advertiser;
}
