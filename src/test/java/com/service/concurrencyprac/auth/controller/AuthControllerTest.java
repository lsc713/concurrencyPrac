package com.service.concurrencyprac.auth.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.service.concurrencyprac.auth.dto.MemberDTO;
import com.service.concurrencyprac.auth.dto.MemberDTO.SignupRequest;
import com.service.concurrencyprac.auth.service.member.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class AuthControllerTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @DisplayName("회원가입이 가능한지 테스트")
    public void testSignUp() throws Exception {
        //given

        //when

        //then

    }

}