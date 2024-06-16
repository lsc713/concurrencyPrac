package com.service.concurrencyprac.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.concurrencyprac.auth.domain.member.Member.Status;
import com.service.concurrencyprac.auth.domain.member.Member.UserRole;
import com.service.concurrencyprac.auth.domain.member.MemberCommand.SignupMemberRequest;
import com.service.concurrencyprac.auth.domain.member.MemberInfo;
import com.service.concurrencyprac.auth.domain.token.TokenBlackList.TokenType;
import com.service.concurrencyprac.auth.dto.MemberDTO;
import com.service.concurrencyprac.auth.jwt.JwtProvider;
import com.service.concurrencyprac.auth.repository.member.MemberReader;
import com.service.concurrencyprac.auth.service.member.MemberService;
import com.service.concurrencyprac.auth.service.token.AuthService;
import com.service.concurrencyprac.auth.service.token.TokenBlackListService;
import com.service.concurrencyprac.common.response.CommonResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private MemberService memberService;

    @MockBean
    private AuthService authService;

    @Autowired
    private MemberReader memberReader;

    @MockBean
    private TokenBlackListService tokenBlackListService;

    private MemberDTO.SignupRequest signupRequest;
    private MemberInfo memberInfo;

    @BeforeEach
    public void setup() {
        signupRequest = MemberDTO.SignupRequest.builder()
            .email("test@example.com")
            .memberName("Test User")
            .password("password")
            .nickName("testuser")
            .build();

        memberInfo = MemberInfo.builder()
            .id(1L)
            .email("test@example.com")
            .memberName("Test User")
            .memberToken("testuser")
            .status(Status.ACTIVATE)
            .role(UserRole.USER)
            .build();
    }

    @Test
    public void testSignup() throws Exception {
        when(memberService.signup(any(SignupMemberRequest.class))).thenReturn(memberInfo);

        mockMvc.perform(post("/api/v1/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(signupRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("SUCCESS"))
            .andExpect(jsonPath("$.data.email").value(memberInfo.getEmail()));
    }

    @Test
    public void testLogout() throws Exception {
        when(jwtProvider.getJwtFromHeader(any(HttpServletRequest.class), any(TokenType.class)))
            .thenReturn("dummy-token");

        mockMvc.perform(post("/api/v1/auth/logout")
                .header(HttpHeaders.AUTHORIZATION, "Bearer dummy-token"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void testRefresh() throws Exception {
        when(jwtProvider.getJwtFromHeader(any(HttpServletRequest.class), any(TokenType.class)))
            .thenReturn("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNYW9AVGVzdC5jb20iLCJleHAiOjE3MTg0MjMyMDMsImlhdCI6MTcxODQxOTYwMywianRpIjoiNTIwZDdlOTQtYzI1Ni00NDkwLTkyYTMtMDMxMDk1NzE1ZmY5In0.-U9Qm0XGMtgKzoSdCM5XUOdRr_fVG9SwhnIbx6eolrU");
        when(authService.refreshAccessToken(anyString())).thenReturn("Bearer new-access-token");
        // Create HttpHeaders object and add custom header
        HttpHeaders headers = new HttpHeaders();
        headers.add("RefreshToken",
            "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNYW9AVGVzdC5jb20iLCJleHAiOjE3MTg0MjMyMDMsImlhdCI6MTcxODQxOTYwMywianRpIjoiNTIwZDdlOTQtYzI1Ni00NDkwLTkyYTMtMDMxMDk1NzE1ZmY5In0.-U9Qm0XGMtgKzoSdCM5XUOdRr_fVG9SwhnIbx6eolrU");

        mockMvc.perform(post("/api/v1/auth/refresh")
                .headers(headers))
            .andExpect(status().isOk())
            .andExpect(result -> {
                String responseBody = result.getResponse().getContentAsString();
                System.out.println("Response: " + responseBody);
                assertNotNull(responseBody);
            })
            .andExpect(jsonPath("$.result").value("SUCCESS"))
            .andExpect(jsonPath("$.data").value("Bearer new-access-token"));
    }

    @Test
    public void testResetBlacklist() throws Exception {
        mockMvc.perform(delete("/api/v1/auth/blacklist/reset"))
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