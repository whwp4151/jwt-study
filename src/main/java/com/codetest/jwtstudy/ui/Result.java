package com.codetest.jwtstudy.ui;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Code code;
    private String message;
    private T data;

    public static Result createErrorResult(String message) {
        return Result.builder()
            .code(Code.ERROR)
            .message(message)
            .data(null)
            .build();
    }

    public static <T> Result createSuccessResult(T data) {
        return Result.builder()
            .code(Code.SUCCESS)
            .message("")
            .data(data)
            .build();
    }

    public enum Code {
        SUCCESS, ERROR
    }
}
