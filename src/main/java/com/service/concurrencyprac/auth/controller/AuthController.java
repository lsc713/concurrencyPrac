package com.service.concurrencyprac.auth.controller;

import com.service.concurrencyprac.auth.domain.member.MemberCommand.SignupMemberRequest;
import com.service.concurrencyprac.auth.domain.member.MemberInfo;
import com.service.concurrencyprac.auth.domain.token.TokenBlackList.TokenType;
import com.service.concurrencyprac.auth.dto.MemberDTO;
import com.service.concurrencyprac.auth.jwt.JwtProvider;
import com.service.concurrencyprac.auth.service.member.MemberService;
import com.service.concurrencyprac.auth.service.token.AuthService;
import com.service.concurrencyprac.auth.service.token.TokenBlackListService;
import com.service.concurrencyprac.common.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtProvider jwtProvider;
    private final MemberService signupService;
    private final AuthService authService;
    private final TokenBlackListService tokenBlackListService;

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public CommonResponse signUp(@RequestBody @Valid MemberDTO.SignupRequest request) {
        SignupMemberRequest command = request.toCommand();
        MemberInfo response = signupService.signup(command);
        return CommonResponse.success(response);
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        tokenBlackListService.addToBlacklist(
            jwtProvider.getJwtFromHeader(request, TokenType.ACCESS),
            jwtProvider.getJwtFromHeader(request, TokenType.REFRESH)
        );
    }

    @Operation(summary = "Token Refresh")
    @PostMapping("/refresh")
    public CommonResponse refresh(HttpServletRequest request) {
        String accessToken = authService.refreshAccessToken(
            jwtProvider.getJwtFromHeader(request, TokenType.REFRESH));
        return CommonResponse.success(accessToken);
    }

    @Operation(summary = "Token BlackList 초기화 (Batch) : Batch 서버에서 주기적으로 Expired 된 토큰 제거")
    @DeleteMapping("/blacklist/reset")
    public CommonResponse resetBlacklist() {
        tokenBlackListService.removeExpiredTokens();
        return CommonResponse.success(HttpStatus.NO_CONTENT);
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
