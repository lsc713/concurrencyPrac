package com.service.concurrencyprac.auth.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.service.concurrencyprac.payment.entity.post.Post;
import com.service.concurrencyprac.payment.entity.post.PostCommand;
import com.service.concurrencyprac.payment.entity.post.PostCommand.PostingCommand;
import com.service.concurrencyprac.payment.entity.post.PostCommand.UpdateCommand;
import com.service.concurrencyprac.payment.entity.post.PostInfo;
import com.service.concurrencyprac.payment.entity.post.PostReader;
import com.service.concurrencyprac.payment.entity.post.PostStore;
import com.service.concurrencyprac.payment.repository.post.PostRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class PostServiceImplTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostStore postStore;

    @Autowired
    private PostReader postReader;

    @Autowired
    private PostServiceImpl postService;

    @AfterEach
    public void tearDown() {
        postRepository.deleteAllInBatch();
    }

    @Test
    public void testRegisterPostSuccessful() {
        PostingCommand postCommand = createPostCommand();

        PostInfo postInfo = postService.registerPost(postCommand, "test@User");

        assertNotNull(postInfo);
        assertEquals("Test Title", postInfo.getTitle());
    }

    @Test
    public void testGetPostInfo() {
        Post post = createPost();
        postStore.store(post);

        PostInfo postInfo = postService.getPostInfo(post.getPostToken());

        assertNotNull(postInfo);
        assertEquals("Test Title", postInfo.getTitle());
    }

    @Test
    public void testFetchAllPosts() {
        Post post = createPost();
        postStore.store(post);

        List<PostInfo> posts = postService.fetchAllPosts();

        assertNotNull(posts);
        assertEquals(1, posts.size());
        assertEquals("Test Title", posts.get(0).getTitle());
    }

    @Test
    public void testUpdatePost() {
        Post post = createPost();
        postStore.store(post);

        UpdateCommand command = updateToCommand();
        PostInfo postInfo = postService.updatePost(post.getPostToken(), command);

        assertNotNull(postInfo);
        assertEquals("Updated Title", postInfo.getTitle());
        assertEquals("Updated Content", postInfo.getContents());
    }

    @Test
    public void testDeletePost() {
        Post post = createPost();
        postStore.store(post);

        postService.deletePost(post.getPostToken(), "testUser");

        assertEquals(0, postRepository.count());
    }

    private PostingCommand createPostCommand() {
        return PostingCommand.builder()
            .title("Test Title")
            .contents("Test Content")
            .nickName("TestNickName")
            .build();
    }

    private PostCommand.UpdateCommand updateToCommand() {
        return UpdateCommand.builder()
            .title("Updated Title")
            .contents("Updated Content")
            .build();
    }

    private Post createPost() {
        return Post.builder()
            .title("Test Title")
            .contents("Test Content")
            .nickName("TestNickName")
            .build();
    }
}