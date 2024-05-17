package com.service.concurrencyprac.api.domain.post;

import java.util.List;

public interface PostReader {

    Post getPost(String postToken);

    Post getPost(Long postId);

    List<Post> getAllPosts();

}
