package com.service.concurrencyprac.api.repository.post;

import com.service.concurrencyprac.api.domain.post.Post;
import com.service.concurrencyprac.api.domain.post.PostStore;
import com.service.concurrencyprac.common.exception.InvalidParamException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PostStoreImpl implements PostStore {

    private final PostRepository postRepository;

    @Override
    public Post store(Post post) {
        if (StringUtils.isEmpty(post.getNickName())) throw new InvalidParamException("signupMember.getPartnerToken()");
        if (StringUtils.isEmpty(post.getTitle())) throw new InvalidParamException("signupMember.getPartnerToken()");
        if (StringUtils.isEmpty(post.getContents())) throw new InvalidParamException("signupMember.getPartnerToken()");

        return postRepository.save(post);
    }
}
