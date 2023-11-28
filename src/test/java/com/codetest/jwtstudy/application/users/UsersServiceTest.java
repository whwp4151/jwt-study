package com.codetest.jwtstudy.application.users;

import static com.codetest.jwtstudy.fixture.UsersFixtures.createLoginRequest;
import static com.codetest.jwtstudy.fixture.UsersFixtures.createSignupRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.codetest.jwtstudy.application.users.UsersDtos.LoginRequest;
import com.codetest.jwtstudy.application.users.UsersDtos.SignupRequest;
import com.codetest.jwtstudy.application.users.UsersDtos.SignupResponse;
import com.codetest.jwtstudy.domain.users.Users;
import com.codetest.jwtstudy.domain.users.UsersRepository;
import com.codetest.jwtstudy.security.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UsersServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UsersService usersService;

    @Spy
    private PasswordEncoder passwordEncoder;

    @Spy
    private AuthenticationManager authenticationManager;

    @Test
    @DisplayName("회원가입")
    public void signup() throws Exception {
        //given 특정 회원 없는 경우
        SignupRequest signupRequest = createSignupRequest();

        when(usersRepository.existsByUserId(any(String.class))).thenReturn(false);
        when(usersRepository.existsByRegNo(any(String.class))).thenReturn(false);

        //when 회원가입을 시도하면
        SignupResponse signupResponse = usersService.signup(signupRequest);

        //then 회원을 저장한다.
        assertEquals(signupRequest.getUserId(), signupResponse.getUserId());
        verify(usersRepository, times(1)).save(any(Users.class));
    }

    @Test
    @DisplayName("로그인")
    public void login() throws Exception {
        //given 특정 회원이 없는 경우
        LoginRequest loginRequest = createLoginRequest();

        when(authenticationManager.authenticate(any())).thenThrow(InternalAuthenticationServiceException.class);

        //when 로그인을 시도하면

        //then 예외가 발생한다.
        assertThrows(CustomException.class, () -> usersService.login(loginRequest));
    }

}