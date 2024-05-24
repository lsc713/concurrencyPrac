package com.service.concurrencyprac.payment.entity.post;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class PostCommand {

    @Getter
    @Builder
    @ToString
    public static class PostingCommand {

        private final String title;
        private final String contents;
        private final String nickName;

        public Post toEntity() {
            return Post.builder()
                .title(title)
                .contents(contents)
                .nickName(nickName)
                .build();
        }
    }

}
