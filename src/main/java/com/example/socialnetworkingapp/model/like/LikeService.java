package com.example.socialnetworkingapp.model.like;

import com.example.socialnetworkingapp.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    public List<Like> findLikesByUserId(Long id) {
        return this.likeRepository.findAllByUserId(id).orElseThrow(() -> new UserNotFoundException("User by id "+ id + "was not found !"));
    }

    public Like addLike(Like like) {
        return this.likeRepository.save(like);
    }

    public void deleteLike(Long user_id, Long post_id) {
        this.likeRepository.deleteByUserAndPost(user_id, post_id);
    }
}