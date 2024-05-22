package com.service.concurrencyprac.payment.controller;

import static com.service.concurrencyprac.payment.entity.post.PostCommand.*;
import static com.service.concurrencyprac.payment.dto.PostDTO.*;

import com.service.concurrencyprac.payment.entity.post.PostInfo;
import com.service.concurrencyprac.payment.repository.post.PostService;
import com.service.concurrencyprac.common.response.CommonResponse;
import com.service.concurrencyprac.security.service.UserDetailsImpl;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
@RestController
public class PostController {

    private final PostService postservice;

    @PostMapping
    public CommonResponse registerPost(
        @RequestBody @Valid PostRequest request,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostingCommand postCommand = request.toCommand();
        PostInfo response = postservice.registerPost(postCommand, userDetails.getUsername());
        return CommonResponse.success(response);
    }

    @GetMapping("/{postToken}")
    public CommonResponse fetchPostOne(@PathVariable String postToken) {
        PostInfo response = postservice.getPostInfo(postToken);
        return CommonResponse.success(response);
    }

    @GetMapping
    public CommonResponse<List<PostInfo>> fetchPosts() {
        List<PostInfo> response = postservice.fetchAllPosts();
        return CommonResponse.success(response);
    }


}
