package com.codetest.jwtstudy.ui.api;

import static com.codetest.jwtstudy.fixture.UsersFixtures.createLoginRequest;
import static com.codetest.jwtstudy.fixture.UsersFixtures.createSignupRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codetest.jwtstudy.application.users.UsersDtos.LoginRequest;
import com.codetest.jwtstudy.application.users.UsersDtos.SignupRequest;
import com.codetest.jwtstudy.application.users.UsersService;
import com.codetest.jwtstudy.security.CustomException;
import com.codetest.jwtstudy.security.LoginResolver;
import com.codetest.jwtstudy.ui.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class UsersControllerTest {

    @Mock
    private UsersService usersService;

    @InjectMocks
    private UsersController usersController;

    @Mock
    private LoginResolver loginResolver;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders
            .standaloneSetup(usersController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .setCustomArgumentResolvers(loginResolver)
            .build();
    }

    @Test
    @DisplayName("회원가입")
    public void signup() throws Exception {
        //given
        SignupRequest signupRequest = createSignupRequest();

        //when
        ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.post("/szs/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(signupRequest))
        );

        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("code").value("SUCCESS"))
            .andDo(print());
    }

    @Test
    @DisplayName("로그인")
    public void login() throws Exception {
        //given
        LoginRequest loginRequest = createLoginRequest();

        when(usersService.login(any()))
            .thenThrow(new CustomException(HttpStatus.BAD_REQUEST, "회원 정보가 일치하지 않습니다."));

        //when
        ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.post("/szs/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(loginRequest))
        );

        //then
        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("code").value("ERROR"))
            .andDo(print());
    }

    @Test
    @DisplayName("내 정보 보기")
    public void me() throws Exception {
        //given
        String token = "token";

        when(loginResolver.supportsParameter(any())).thenReturn(true);
        when(loginResolver.resolveArgument(any(), any(), any(), any()))
            .thenThrow(new CustomException(HttpStatus.BAD_REQUEST, "로그인 정보가 없습니다."));

        //when
        ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.get("/szs/me")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
        );

        //then
        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("code").value("ERROR"))
            .andDo(print());
    }

}