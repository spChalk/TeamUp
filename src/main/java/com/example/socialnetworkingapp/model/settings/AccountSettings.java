package com.example.socialnetworkingapp.model.settings;
import com.example.socialnetworkingapp.account.Account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "account_settings")
@AllArgsConstructor
@NoArgsConstructor
public class AccountSettings implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id ;

    @Column(name = "setting_name", nullable = false)
    private String settingName;

    @Column(name = "setting_value", nullable = false)
    private String settingValue;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name ="account_id", nullable = false)
    private Account user;
}