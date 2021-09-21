package com.example.socialnetworkingapp.model.like;

import com.example.socialnetworkingapp.exception.UserNotFoundException;
import com.example.socialnetworkingapp.mapper.LikeMapper;
import com.example.socialnetworkingapp.model.comment.CommentResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final LikeMapper likeMapper;

    public List<Like> findLikesByUserId(Long id) {
        return this.likeRepository.findAllByUserId(id).orElseThrow(() -> new UserNotFoundException("User by id "+ id + "was not found !"));
    }

    public LikeResponse addLike(Like like) {
        Like l = this.likeRepository.save(like);
        return new LikeResponse(l.getId(), l.getUser().getFirstName(), l.getUser().getLastName(), l.getUser().getEmail());
    }

    public void deleteLike(Long user_id, Long post_id) {
        this.likeRepository.deleteByUserAndPost(user_id, post_id);
    }

    public void deleteLikeById(Long likeId) {
        this.likeRepository.deleteById(likeId);
    }

    public List<LikeResponse> findAllLikesOfPost(Long pid) {
        return this.likeRepository.findLikesOfPostById(pid).stream().map(likeMapper::LikeToLikeResponse).
                collect(Collectors.toList());
    }

    public List<LikeResponse> findAllLikesOfUser(Long id) {
        return this.likeRepository.findAllByAccountId(id).stream().map(likeMapper::LikeToLikeResponse).collect(Collectors.toList());
    }
}