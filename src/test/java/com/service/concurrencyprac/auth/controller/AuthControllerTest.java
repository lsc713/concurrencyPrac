package com.service.concurrencyprac.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.concurrencyprac.auth.domain.member.Member;
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
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private MemberService memberService;

    @MockBean
    private AuthService authService;

    @MockBean
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
        String refreshToken = "dummy-refresh-token";
        String newAccessToken = "Bearer new-access-token";

        // JWT 관련 Mock 설정
        when(jwtProvider.getJwtFromHeader(any(HttpServletRequest.class), Mockito.eq(TokenType.REFRESH)))
            .thenReturn(refreshToken);
        when(jwtProvider.getJwtFromHeader(any(HttpServletRequest.class), Mockito.eq(TokenType.ACCESS)))
            .thenReturn(null);
        when(jwtProvider.validateToken(anyString())).thenReturn(true);

        Claims claims = Mockito.mock(Claims.class);
        when(claims.getSubject()).thenReturn("test@example.com");
        when(jwtProvider.getUserInfoFromToken(anyString())).thenReturn(claims);

        // MemberReader Mock 설정
        Member mockMember = Member.builder()
            .email("test@example.com")
            .password("password")
            .name("Test User")
            .nickName("testuser")
            .role(UserRole.USER)
            .build();
        when(memberReader.getMember(anyString())).thenReturn(mockMember);

        when(authService.refreshAccessToken(anyString())).thenReturn(newAccessToken);

        HttpHeaders headers = new HttpHeaders();
        headers.add("RefreshToken", refreshToken);

        mockMvc.perform(post("/api/v1/auth/refresh")
                .headers(headers))
            .andExpect(status().isOk())
            .andExpect(result -> {
                String responseBody = result.getResponse().getContentAsString();
                System.out.println("Response: " + responseBody);
                assertNotNull(responseBody);
            })
            .andExpect(jsonPath("$.result").value("SUCCESS"))
            .andExpect(jsonPath("$.data").value(newAccessToken));
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