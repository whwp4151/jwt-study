package com.codetest.jwtstudy.fixture;

import com.codetest.jwtstudy.application.users.UsersDtos.LoginRequest;
import com.codetest.jwtstudy.application.users.UsersDtos.SignupRequest;
import com.codetest.jwtstudy.domain.users.Users;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UsersFixtures {

    public static Users createUser(String userId, String regNo, PasswordEncoder passwordEncoder) {
        return new Users(userId, "123456", "홍길동", regNo, passwordEncoder);
    }

    public static SignupRequest createSignupRequest() {
        return SignupRequest.builder()
            .userId("hong12")
            .password("123456")
            .name("홍길동")
            .regNo("111111-1111111")
            .build();
    }

    public static LoginRequest createLoginRequest() {
        return LoginRequest.builder()
            .userId("hong12")
            .password("123456")
            .build();
    }

}
