package com.service.concurrencyprac.api.controller;

import static com.service.concurrencyprac.api.domain.post.PostCommand.*;
import static com.service.concurrencyprac.api.dto.PostDTO.*;

import com.service.concurrencyprac.api.domain.post.PostCommand;
import com.service.concurrencyprac.api.domain.post.PostInfo;
import com.service.concurrencyprac.api.domain.post.PostService;
import com.service.concurrencyprac.api.dto.PostDTO;
import com.service.concurrencyprac.common.response.CommonResponse;
import com.service.concurrencyprac.security.service.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

}
