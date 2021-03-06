package com.example.socialnetworkingapp.model.job_view;

import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.job.Job;
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
@Table(name = "job_views")
public class JobView implements Serializable {

    @Id
    @TableGenerator(name = "Id_Gen", initialValue = 1001)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Id_Gen")
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Account viewer;

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
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