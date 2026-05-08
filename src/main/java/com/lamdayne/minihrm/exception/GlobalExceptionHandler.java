package com.lamdayne.minihrm.exception;

import com.lamdayne.minihrm.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<?>> handleAppException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ApiResponse.builder()
                        .message(errorCode.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String enumKey = e.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_ERROR_CODE;

        try {
            errorCode = ErrorCode.valueOf(enumKey);
        } catch (IllegalArgumentException ex) {
            String fieldError = e.getFieldError().getField();
            String classError = e.getFieldError().getObjectName();
            log.error("Invalid ErrorCode with key = \"{}\", field = \"{}\", classError = \"{}\"", enumKey, fieldError, classError);
        }

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ApiResponse.builder()
                        .status(errorCode.getStatus())
                        .message(errorCode.getMessage())
                        .build()
                );
    }

}
