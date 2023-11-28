package com.codetest.jwtstudy.security;

import com.codetest.jwtstudy.application.users.UsersService;
import com.codetest.jwtstudy.domain.users.Users;
import com.codetest.jwtstudy.utils.SecurityUtil;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class LoginResolver implements HandlerMethodArgumentResolver {

    private final UsersService usersService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Login.class);
    }

    @Override
    public Users resolveArgument(
        MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory) throws Exception {

        Optional<String> currentUsername = SecurityUtil.getCurrentUsername();

        if (currentUsername.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "로그인 정보가 없습니다.");
        }

        return usersService.getByUserId(currentUsername.get());
    }

}
