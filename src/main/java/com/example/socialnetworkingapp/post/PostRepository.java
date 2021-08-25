package com.example.socialnetworkingapp.post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {
    void deletePostById(Long id);
    Optional<Post> findPostById(Long id);
}
