package com.service.concurrencyprac.payment.entity.post;

import java.util.List;

public interface PostReader {

    Post getPost(String postToken);

    Post getPost(Long postId);

    List<Post> getAllPosts();

}
