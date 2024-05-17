package com.service.concurrencyprac.api.domain.post;

public interface PostReader {

    Post getPost(String postToken);

    Post getPost(Long postId);

}
