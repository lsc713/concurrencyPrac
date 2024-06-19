package com.service.concurrencyprac.security.service;

import com.service.concurrencyprac.auth.domain.token.TokenBlackList.TokenType;
import com.service.concurrencyprac.auth.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class JwtAuthorizationFilterTest {

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @InjectMocks
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    @Mock
    private FilterChain filterChain;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDoFilterInternal_withValidAccessToken() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addHeader("Authorization", "Bearer valid-access-token");

        when(jwtProvider.getJwtFromHeader(any(), eq(TokenType.ACCESS))).thenReturn("valid-access-token");
        when(jwtProvider.validateToken("valid-access-token")).thenReturn(true);
        Claims claims = mock(Claims.class);
        when(claims.getSubject()).thenReturn("test@example.com");
        when(jwtProvider.getUserInfoFromToken("valid-access-token")).thenReturn(claims);
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername("test@example.com")).thenReturn(userDetails);

        jwtAuthorizationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(userDetailsService).loadUserByUsername("test@example.com");
    }

    @Test
    public void testDoFilterInternal_withInvalidAccessToken() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addHeader("Authorization", "Bearer invalid-access-token");

        when(jwtProvider.getJwtFromHeader(any(), eq(TokenType.ACCESS))).thenReturn("invalid-access-token");
        when(jwtProvider.validateToken("invalid-access-token")).thenReturn(false);

        jwtAuthorizationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, never()).doFilter(request, response);
        verify(userDetailsService, never()).loadUserByUsername(anyString());
    }

    @Test
    public void testDoFilterInternal_withValidRefreshToken() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addHeader("Authorization", "Bearer valid-refresh-token");

        when(jwtProvider.getJwtFromHeader(any(), eq(TokenType.REFRESH))).thenReturn("valid-refresh-token");
        when(jwtProvider.validateToken("valid-refresh-token")).thenReturn(true);
        Claims claims = mock(Claims.class);
        when(claims.getSubject()).thenReturn("test@example.com");
        when(jwtProvider.getUserInfoFromToken("valid-refresh-token")).thenReturn(claims);
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername("test@example.com")).thenReturn(userDetails);

        jwtAuthorizationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(userDetailsService).loadUserByUsername("test@example.com");
    }

    @Test
    public void testDoFilterInternal_withInvalidRefreshToken() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addHeader("Authorization", "Bearer invalid-refresh-token");

        when(jwtProvider.getJwtFromHeader(any(), eq(TokenType.REFRESH))).thenReturn("invalid-refresh-token");
        when(jwtProvider.validateToken("invalid-refresh-token")).thenReturn(false);

        jwtAuthorizationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, never()).doFilter(request, response);
        verify(userDetailsService, never()).loadUserByUsername(anyString());
    }

    @Test
    public void testDoFilterInternal_withExceptionWhileSettingAuthenticationForAccessToken() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addHeader("Authorization", "Bearer valid-access-token");

        when(jwtProvider.getJwtFromHeader(any(), eq(TokenType.ACCESS))).thenReturn("valid-access-token");
        when(jwtProvider.validateToken("valid-access-token")).thenReturn(true);
        Claims claims = mock(Claims.class);
        when(claims.getSubject()).thenReturn("test@example.com");
        when(jwtProvider.getUserInfoFromToken("valid-access-token")).thenReturn(claims);
        doThrow(new RuntimeException("User not found")).when(userDetailsService).loadUserByUsername("test@example.com");

        jwtAuthorizationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, never()).doFilter(request, response);
        verify(userDetailsService).loadUserByUsername("test@example.com");
    }

    @Test
    public void testDoFilterInternal_withExceptionWhileSettingAuthenticationForRefreshToken() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addHeader("Authorization", "Bearer valid-refresh-token");

        when(jwtProvider.getJwtFromHeader(any(), eq(TokenType.REFRESH))).thenReturn("valid-refresh-token");
        when(jwtProvider.validateToken("valid-refresh-token")).thenReturn(true);
        Claims claims = mock(Claims.class);
        when(claims.getSubject()).thenReturn("test@example.com");
        when(jwtProvider.getUserInfoFromToken("valid-refresh-token")).thenReturn(claims);
        doThrow(new RuntimeException("User not found")).when(userDetailsService).loadUserByUsername("test@example.com");

        jwtAuthorizationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, never()).doFilter(request, response);
        verify(userDetailsService).loadUserByUsername("test@example.com");
    }
}