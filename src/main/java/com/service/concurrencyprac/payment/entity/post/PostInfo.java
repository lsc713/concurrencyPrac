package com.service.concurrencyprac.payment.entity.post;

import lombok.Getter;

@Getter
public class PostInfo {

    private final Long id;
    private final String nickName;
    private final String title;
    private final String contents;
    private final String postToken;
    private final Post.Status status;

    public PostInfo(Post post) {
        this.id = post.getId();
        this.nickName = post.getAuthor();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.postToken = post.getPostToken();
        this.status = post.getStatus();
    }
}
