package com.example.socialnetworkingapp.model.post_view;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PostViewRepository extends JpaRepository<PostView, Long> {

    @Query("SELECT pv FROM PostView pv WHERE pv.post.id = ?1")
    Optional<List<PostView>> getViewsByPostId(Long id);

    @Query("SELECT pv FROM PostView pv WHERE pv.viewer.id = ?1 AND pv.post.id = ?2")
    Optional<PostView> findViewByIds(Long uid, Long jid);

    @Query("SELECT pv FROM PostView pv WHERE pv.viewer.id = ?1 AND pv.id = ?2")
    Optional<PostView> getSumOfViewsOfPostByUser(Long uid, Long pid);
}
