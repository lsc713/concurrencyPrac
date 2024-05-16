package com.service.concurrencyprac.api.domain.post;

import com.service.concurrencyprac.api.domain.BaseEntity;
import com.service.concurrencyprac.common.util.TokenGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comments;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class Post extends BaseEntity {

    private static final String PREFIX_POST = "post_";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userToken;
    private String title;
    private String contents;
    private String postToken;

//    private List<Comment> comments;
    //private Long thumbUp;

    @Builder
    public Post(String userToken, String title, String contents) {
        this.postToken = TokenGenerator.randomCharacterWithPrefix(PREFIX_POST);
        this.userToken = userToken;
        this.title = title;
        this.contents = contents;


    }
}
