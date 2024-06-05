package com.service.concurrencyprac.security.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.concurrencyprac.auth.domain.member.Member;
import com.service.concurrencyprac.auth.dto.LoginRequestDto;
import com.service.concurrencyprac.auth.jwt.JwtProvider;
import com.service.concurrencyprac.auth.repository.AccessLogRepository;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class JwtAuthenticationFilterTest {

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private AccessLogRepository accessLogRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        mockMvc = MockMvcBuilders.standaloneSetup(jwtAuthenticationFilter).build();
    }

    @Test
    public void testAttemptAuthentication() throws JsonProcessingException,Exception {
        LoginRequestDto loginRequest = new LoginRequestDto("test@example.com", "password");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContent(new ObjectMapper().writeValueAsBytes(loginRequest));
        MockHttpServletResponse response = new MockHttpServletResponse();

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
            loginRequest.getEmail(), loginRequest.getPassword(), null);
        when(authenticationManager.authenticate(any())).thenReturn(authRequest);

        Authentication authentication = jwtAuthenticationFilter.attemptAuthentication(request, response);

        assertEquals(authRequest, authentication);
        verify(authenticationManager).authenticate(any());
    }

    @Test
    public void testSuccessfulAuthentication() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        Member member = mock(Member.class);

        when(userDetails.getMember()).thenReturn(member);
        when(member.getEmail()).thenReturn("test@example.com");
        when(jwtProvider.createToken(any())).thenReturn("accessToken", "refreshToken");

        Authentication authResult = new UsernamePasswordAuthenticationToken(userDetails, null, null);

        jwtAuthenticationFilter.successfulAuthentication(request, response, filterChain, authResult);

        assertEquals("accessToken", response.getHeader(JwtProvider.ACCESS_TOKEN_HEADER));
        assertEquals("refreshToken", response.getHeader(JwtProvider.REFRESH_TOKEN_HEADER));
        verify(accessLogRepository).save(any());
    }

    @Test
    public void testUnsuccessfulAuthentication() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        AuthenticationException exception = mock(AuthenticationException.class);

        jwtAuthenticationFilter.unsuccessfulAuthentication(request, response, exception);

        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
    }
}