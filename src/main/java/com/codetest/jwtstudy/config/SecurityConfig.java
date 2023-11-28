package com.codetest.jwtstudy.config;

import com.codetest.jwtstudy.application.users.CustomUserDetailsService;
import com.codetest.jwtstudy.security.JwtAccessDeniedHandler;
import com.codetest.jwtstudy.security.JwtAuthenticationEntryPoint;
import com.codetest.jwtstudy.security.JwtSecurityConfig;
import com.codetest.jwtstudy.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final TokenProvider tokenProvider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
            http.getSharedObject(AuthenticationManagerBuilder.class);
        return authenticationManagerBuilder
            .userDetailsService(customUserDetailsService)
            .passwordEncoder(passwordEncoder())
            .and()
            .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()

            .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
            .and()

            .authorizeHttpRequests()
                .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**").permitAll()
                .antMatchers("/szs/signup", "/szs/login").permitAll()
                .requestMatchers(PathRequest.toH2Console()).permitAll()
                .anyRequest().authenticated()
            .and()

            .headers().frameOptions().disable()
            .and()

            .apply(new JwtSecurityConfig(tokenProvider));

        return http.build();
    }

}
