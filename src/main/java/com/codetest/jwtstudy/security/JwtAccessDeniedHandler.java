package com.codetest.jwtstudy.security;

import com.codetest.jwtstudy.ui.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(new ObjectMapper().writeValueAsString(Result.createErrorResult("Forbidden")));
    }

}
