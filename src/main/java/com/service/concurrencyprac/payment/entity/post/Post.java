package com.service.concurrencyprac.payment.entity.post;

import com.service.concurrencyprac.auth.domain.BaseEntity;
import com.service.concurrencyprac.common.util.TokenGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class Post extends BaseEntity {

    private static final String PREFIX_POST = "post_";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickName;
    private String title;
    private String contents;
    private String postToken;
    private Status status;

//    private List<Comment> comments;
    //private Long thumbUp;

    @Builder
    public Post(String nickName, String title, String contents) {
        this.postToken = TokenGenerator.randomCharacterWithPrefix(PREFIX_POST);
        this.nickName = nickName;
        this.title = title;
        this.contents = contents;
        this.status = Status.ACTIVATE;

    }

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        ACTIVATE("활성화"), DISABLE("비활성화");
        public final String description;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Category {
        Movie("영화"), Daily("일상");
        public final String description;
    }
}
