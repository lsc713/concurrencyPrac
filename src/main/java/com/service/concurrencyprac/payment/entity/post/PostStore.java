package com.service.concurrencyprac.payment.entity.post;

public interface PostStore {

    Post store(Post post);

    void delete(Post post);

}
