package com.service.concurrencyprac.auth.controller;

import com.service.concurrencyprac.auth.domain.member.MemberCommand.SignupMemberRequest;
import com.service.concurrencyprac.auth.domain.member.MemberInfo;
import com.service.concurrencyprac.auth.dto.MemberDTO;
import com.service.concurrencyprac.auth.service.impl.MemberServiceImpl;
import com.service.concurrencyprac.common.response.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/signup")
@RequiredArgsConstructor
public class AuthController {

    private final MemberServiceImpl signupService;

    @PostMapping
    public CommonResponse signUp(@RequestBody @Valid MemberDTO.SignupRequest request) {
        SignupMemberRequest command = request.toCommand();
        MemberInfo response = signupService.signup(command);
        return CommonResponse.success(response);
    }

    @GetMapping("/admin")
    public String adminPage() {
        return null;
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
