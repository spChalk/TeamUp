package com.example.socialnetworkingapp.model.job_view;

import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.job.Job;
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
@Table(name = "views")
public class JobView implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull
    private Account viewer;

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull
    private Job job;

    @NotNull
    private Long times;

    public void increaseViews() {
        this.times++;
    }

    public JobView(Account viewer, Job job) {
        this.viewer = viewer;
        this.job = job;
        this.times = 1L;
    }
}