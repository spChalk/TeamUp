package com.example.socialnetworkingapp.model.like;

import com.example.socialnetworkingapp.model.bio.Bio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("SELECT l FROM Like l WHERE l.user.id = ?1")
    Optional<List<Like>> findAllByUserId(Long id);

    @Query("DELETE FROM Like l WHERE l.user.id = ?1 AND l.post.id = ?2")
    void deleteByUserAndPost(Long user_id, Long post_id);
}
