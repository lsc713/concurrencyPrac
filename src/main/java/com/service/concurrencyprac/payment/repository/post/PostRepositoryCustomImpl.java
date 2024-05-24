package com.service.concurrencyprac.payment.repository.post;

import static com.service.concurrencyprac.auth.domain.post.QPost.post;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.service.concurrencyprac.config.QuerydslConfig;
import com.service.concurrencyprac.payment.entity.post.Post;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final QuerydslConfig querydslConfig;

    @Override
    public List<Post> searchItems(String keyword) {
        return querydslConfig.jpaQueryFactory().selectFrom(post)
            .where(equalsType(keyword))
            .orderBy(post.createdAt.desc())
            .fetch();
    }

    private BooleanExpression equalsType(String keyword) {
        return post.title.contains(keyword);
    }
}
