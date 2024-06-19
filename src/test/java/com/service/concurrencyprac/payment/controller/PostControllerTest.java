package com.service.concurrencyprac.payment.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.concurrencyprac.auth.domain.member.Member;
import com.service.concurrencyprac.auth.domain.member.Member.UserRole;
import com.service.concurrencyprac.payment.entity.post.Post;
import com.service.concurrencyprac.payment.entity.post.PostCommand.PostingCommand;
import com.service.concurrencyprac.payment.entity.post.PostCommand.UpdateCommand;
import com.service.concurrencyprac.payment.entity.post.PostInfo;
import com.service.concurrencyprac.payment.repository.post.PostService;
import com.service.concurrencyprac.security.service.UserDetailsImpl;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    private PostInfo postInfo;
    private UserDetailsImpl userDetails;

    @BeforeEach
    public void setup() {
        userDetails = new UserDetailsImpl(
            Member.builder()
                .email("test@example.com")
                .password("password")
                .name("Test Name")
                .nickName("TestNickName")
                .role(UserRole.USER)
                .build()
        );

        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities())
        );

        postInfo = new PostInfo(
            Post.builder()
                .title("Test Title")
                .contents("Test Contents")
                .nickName("TestNickName")
                .build()
        );
    }

    @Test
    @WithMockUser(username = "test@example.com")
    public void testRegisterPost() throws Exception {
        PostingCommand postingCommand = PostingCommand.builder()
            .title("Test Title")
            .contents("Test Contents")
            .nickName("TestNickName")
            .build();

        when(postService.registerPost(any(PostingCommand.class)))
            .thenReturn(postInfo);

        mockMvc.perform(post("/api/v1/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postingCommand))
                .principal(() -> "test@example.com"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.title").value(postInfo.getTitle()))
            .andExpect(jsonPath("$.data.contents").value(postInfo.getContents()));
    }

    @Test
    @WithMockUser("testUser")
    public void testFetchPostOne() throws Exception {
        when(postService.getPostInfo(any(String.class)))
            .thenReturn(postInfo);

        mockMvc.perform(get("/api/v1/post/fetch/{postToken}", "test-token"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.title").value(postInfo.getTitle()))
            .andExpect(jsonPath("$.data.contents").value(postInfo.getContents()));
    }

    @Test
    @WithMockUser("testUser")
    public void testFetchPosts() throws Exception {
        List<PostInfo> postInfoList = Collections.singletonList(postInfo);

        when(postService.fetchAllPosts())
            .thenReturn(postInfoList);

        mockMvc.perform(get("/api/v1/post/fetch"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data[0].title").value(postInfo.getTitle()))
            .andExpect(jsonPath("$.data[0].contents").value(postInfo.getContents()));
    }

    @Test
    @WithMockUser
    public void testUpdatePost() throws Exception {
        UpdateCommand updateCommand = UpdateCommand.builder()
            .title("Updated Title")
            .contents("Updated Contents")
            .build();

        PostInfo updatedPostInfo = new PostInfo(
            Post.builder()
                .title("Updated Title")
                .contents("Updated Contents")
                .nickName("TestNickName")
                .build()
        );

        when(postService.updatePost(any(String.class), any(UpdateCommand.class)))
            .thenReturn(updatedPostInfo);

        mockMvc.perform(put("/api/v1/post/{postToken}", "test-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updateCommand))
                .principal(() -> "test@example.com"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.title").value(updatedPostInfo.getTitle()))
            .andExpect(jsonPath("$.data.contents").value(updatedPostInfo.getContents()));
    }

    @Test
    @WithMockUser(username = "testUser")
    public void testDeletePost() throws Exception {
        mockMvc.perform(delete("/api/v1/post/{postToken}", "test-token")
                .principal(() -> "test@example.com"))
            .andExpect(status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}