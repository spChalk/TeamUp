package com.example.socialnetworkingapp.model.job_view;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface JobViewRepository extends JpaRepository<JobView, Long> {

    @Query("SELECT jv FROM JobView jv WHERE jv.job.id = ?1")
    Optional<List<JobView>> getViewsByJobId(Long id);

    @Query("SELECT jv FROM JobView jv WHERE jv.viewer.id = ?1 AND jv.job.id = ?2")
    Optional<JobView> findViewByIds(Long uid, Long jid);
}
