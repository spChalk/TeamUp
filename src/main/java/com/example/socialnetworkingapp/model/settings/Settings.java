package com.example.socialnetworkingapp.model.settings;
import com.example.socialnetworkingapp.model.account.Account;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "settings")
public class Settings implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id ;

    @Column(nullable = false)
    private String settingName;

    @Column(nullable = false)
    private String settingValue;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Account user;
}