package com.codetest.jwtstudy.ui.api;

import com.codetest.jwtstudy.application.users.UsersDtos.LoginRequest;
import com.codetest.jwtstudy.application.users.UsersDtos.LoginResponse;
import com.codetest.jwtstudy.application.users.UsersDtos.SignupRequest;
import com.codetest.jwtstudy.application.users.UsersDtos.SignupResponse;
import com.codetest.jwtstudy.application.users.UsersDtos.UserResponse;
import com.codetest.jwtstudy.application.users.UsersService;
import com.codetest.jwtstudy.domain.users.Users;
import com.codetest.jwtstudy.security.Login;
import com.codetest.jwtstudy.ui.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원", description = "회원 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/szs")
public class UsersController {

    private final UsersService usersService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "회원가입 API", tags = "회원")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = SignupResponse.class))),
        @ApiResponse(responseCode = "400", description = "필수 입력값 누락"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @io.swagger.annotations.ApiResponses(
        @io.swagger.annotations.ApiResponse(code = 200, message = "Success", response = SignupResponse.class)
    )
    public ResponseEntity<Result> signup(@RequestBody @Valid SignupRequest request) {
        SignupResponse response = usersService.signup(request);
        return ResponseEntity.ok(Result.createSuccessResult(response));
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "가입한 회원을 로그인 한다.", tags = "회원")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = LoginResponse.class))),
        @ApiResponse(responseCode = "400", description = "필수 입력값 누락, 회원 정보가 일치하지 않습니다."),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @io.swagger.annotations.ApiResponses(
        @io.swagger.annotations.ApiResponse(code = 200, message = "Success", response = LoginResponse.class)
    )
    public ResponseEntity<Result> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse response = usersService.login(request);
        return ResponseEntity.ok(Result.createSuccessResult(response));
    }

    @GetMapping("/me")
    @Operation(summary = "내 정보 보기", description = "가입한 회원의 정보를 가져온다.", tags = "회원")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @io.swagger.annotations.ApiResponses(
        @io.swagger.annotations.ApiResponse(code = 200, message = "Success", response = UserResponse.class)
    )
    public ResponseEntity<Result> me(@Login Users user) {
        UserResponse response = UserResponse.of(user);
        return ResponseEntity.ok(Result.createSuccessResult(response));
    }

}
