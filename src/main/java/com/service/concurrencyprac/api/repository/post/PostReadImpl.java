package com.service.concurrencyprac.api.repository.post;

import com.service.concurrencyprac.api.domain.post.Post;
import com.service.concurrencyprac.api.domain.post.PostReader;
import com.service.concurrencyprac.common.exception.EntityNotFoundException;
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
}
