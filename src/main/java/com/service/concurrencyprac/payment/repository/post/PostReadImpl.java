package com.service.concurrencyprac.payment.repository.post;

import com.service.concurrencyprac.common.exception.EntityNotFoundException;
import com.service.concurrencyprac.payment.entity.post.Post;
import com.service.concurrencyprac.payment.entity.post.PostReader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PostReadImpl implements PostReader {

    private final PostRepository postRepository;

    @Override
    public Post getPost(String postToken) {
        return postRepository.findByPostToken(postToken)
            .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Post getPost(Long postId) {
        return postRepository.findById(postId)
            .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }
}
