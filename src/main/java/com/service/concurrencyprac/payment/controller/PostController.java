package com.service.concurrencyprac.payment.controller;

import static com.service.concurrencyprac.payment.dto.PostDTO.PostRequest;
import static com.service.concurrencyprac.payment.entity.post.PostCommand.PostingCommand;

import com.service.concurrencyprac.common.response.CommonResponse;
import com.service.concurrencyprac.payment.dto.PostDTO.PostUpdateDto;
import com.service.concurrencyprac.payment.entity.post.PostCommand.UpdateCommand;
import com.service.concurrencyprac.payment.entity.post.PostInfo;
import com.service.concurrencyprac.payment.repository.post.PostService;
import com.service.concurrencyprac.security.service.UserDetailsImpl;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
@RestController
public class PostController {

    private final PostService postservice;

    @PostMapping
    public CommonResponse<PostInfo> registerPost(
        @RequestBody @Valid PostRequest request,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostingCommand postCommand = request.toCommand(userDetails.getNickname());
        PostInfo response = postservice.registerPost(postCommand);
        return CommonResponse.success(response);
    }

    @GetMapping("/fetch")
    public CommonResponse<List<PostInfo>> fetchPosts() {
        List<PostInfo> response = postservice.fetchAllPosts();
        return CommonResponse.success(response);
    }

    @GetMapping("/fetch/{postToken}")
    public CommonResponse<PostInfo> fetchPostOne(@PathVariable("postToken") String postToken) {
        PostInfo response = postservice.getPostInfo(postToken);
        return CommonResponse.success(response);
    }

    @PutMapping("/{postToken}")
    public CommonResponse<PostInfo> updatePost(@PathVariable("postToken") String postToken,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody UpdateCommand requestDto) {
        PostInfo result = postservice.updatePost(postToken, requestDto);
        return CommonResponse.success(result);
    }

    @DeleteMapping("/{postToken}")
    public CommonResponse<HttpStatus> deletePost(
        @PathVariable("postToken") String postToken,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        postservice.deletePost(postToken, userDetails.getUsername());
        return CommonResponse.success(HttpStatus.OK);

    }

}
