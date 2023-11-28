package com.codetest.jwtstudy.application.users;

import com.codetest.jwtstudy.application.users.UsersDtos.LoginRequest;
import com.codetest.jwtstudy.application.users.UsersDtos.LoginResponse;
import com.codetest.jwtstudy.application.users.UsersDtos.SignupRequest;
import com.codetest.jwtstudy.application.users.UsersDtos.SignupResponse;
import com.codetest.jwtstudy.domain.users.Users;
import com.codetest.jwtstudy.domain.users.UsersRepository;
import com.codetest.jwtstudy.security.CustomException;
import com.codetest.jwtstudy.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    public SignupResponse signup(SignupRequest request) {
        this.validateDuplicateUserId(request.getUserId());
        this.validateDuplicateRegNo(request.getRegNo());

        Users user = new Users(
            request.getUserId(),
            request.getPassword(),
            request.getName(),
            request.getRegNo(),
            passwordEncoder
        );

        usersRepository.save(user);

        return SignupResponse.of(user);
    }

    private void validateDuplicateUserId(String userId) {
        if (usersRepository.existsByUserId(userId)) {
            throw new CustomException(HttpStatus.CONFLICT, "중복된 아이디 입니다.");
        }
    }

    private void validateDuplicateRegNo(String regNo) {
        if (usersRepository.existsByRegNo(regNo)) {
            throw new CustomException(HttpStatus.CONFLICT, "중복된 사용자 입니다.");
        }
    }

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticate(request.getUserId(), request.getPassword());

        return tokenProvider.createToken(authentication);
    }

    private Authentication authenticate(String username, String password) {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException | InternalAuthenticationServiceException e) {
            log.error(e.getMessage());
            throw new CustomException(HttpStatus.BAD_REQUEST, "회원 정보가 일치하지 않습니다.");
        }
    }

    public Users getByUserId(String userId) {
        return usersRepository.findByUserId(userId)
            .orElseThrow(() ->  new CustomException(HttpStatus.BAD_REQUEST, "회원이 존재하지 않습니다."));
    }

}
