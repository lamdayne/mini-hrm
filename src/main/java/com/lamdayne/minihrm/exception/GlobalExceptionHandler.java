package com.lamdayne.minihrm.exception;

import com.lamdayne.minihrm.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
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
        FieldError fieldError = e.getFieldError();
        if (fieldError == null) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.builder()
                            .message("Validation failed!")
                            .build()
            );
        }

        String enumKey = fieldError.getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_ERROR_CODE;

        try {
            errorCode = ErrorCode.valueOf(enumKey);
        } catch (IllegalArgumentException ex) {
            String fieldEr = e.getFieldError().getField();
            String classError = e.getFieldError().getObjectName();
            log.error("Invalid ErrorCode with key = \"{}\", field = \"{}\", classError = \"{}\"", enumKey, fieldEr, classError);
        }

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ApiResponse.builder()
                        .status(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
                );
    }

}
