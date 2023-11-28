package com.codetest.jwtstudy.application.users;

import com.codetest.jwtstudy.domain.users.Users;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

public class UsersDtos {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Valid
    @Schema(description = "회원가입 요청")
    public static class SignupRequest {

        @NotBlank(message = "아이디는 필수 입력 항목입니다.")
        @Length(min = 4, max = 20, message = "아이디는 4자 이상 20자 이하여야 합니다.")
        @Schema(description = "아이디", example = "hong12", required = true)
        private String userId;

        @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
        @Length(min = 4, max = 20, message = "비밀번호는 4자 이상 20자 이하여야 합니다.")
        @Schema(description = "비밀번호", example = "123456", required = true)
        private String password;

        @NotBlank(message = "이름은 필수 입력 항목입니다.")
        @Length(max = 20, message = "이름은 20자 이하여야 합니다.")
        @Schema(description = "이름", example = "홍길동", required = true)
        private String name;

        @NotBlank(message = "주민등록번호는 필수 입력 항목입니다.")
        @Pattern(regexp = "^[0-9]{6}-[0-9]{7}$", message = "올바른 주민등록번호 형식이 아닙니다.")
        @Schema(description = "주민등록번호", example = "111111-1111111", required = true)
        private String regNo;

    }

    @Getter
    @Builder
    @Schema(description = "회원가입 응답")
    public static class SignupResponse {

        @Schema(description = "id", example = "1")
        private Long id;

        @Schema(description = "아이디", example = "hong12")
        private String userId;

        @Schema(description = "이름", example = "홍길동")
        private String name;

        public static SignupResponse of(Users user) {
            return SignupResponse.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .name(user.getName())
                .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Valid
    @Schema(description = "로그인 요청")
    public static class LoginRequest {

        @NotBlank(message = "아이디는 필수 입력 항목입니다.")
        @Length(min = 4, max = 20, message = "아이디는 4자 이상 20자 이하여야 합니다.")
        @Schema(description = "아이디", example = "hong12", required = true)
        private String userId;

        @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
        @Length(min = 4, max = 20, message = "비밀번호는 4자 이상 20자 이하여야 합니다.")
        @Schema(description = "비밀번호", example = "123456", required = true)
        private String password;

    }

    @Getter
    @AllArgsConstructor
    @Schema(description = "로그인 응답")
    public static class LoginResponse {

        @Schema(description = "토큰", example = "asdf")
        private String token;

        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
        @Schema(description = "만료시간", example = "2021-11-08 11:00:00")
        private LocalDateTime expiresIn;

    }

    @Getter
    @Builder
    @Schema(description = "회원 정보 응답")
    public static class UserResponse {

        @Schema(description = "id", example = "1")
        private Long id;

        @Schema(description = "아이디", example = "hong12")
        private String userId;

        @Schema(description = "이름", example = "홍길동")
        private String name;

        @Schema(description = "주민등록번호", example = "111111-1111111")
        private String regNo;

        public static UserResponse of(Users user) {
            return UserResponse.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .name(user.getName())
                .regNo(user.getRegNo())
                .build();
        }

    }

}
