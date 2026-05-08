package com.lamdayne.minihrm.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ErrorCode {

    ;

    private int status;
    private String message;
    private HttpStatus httpStatus;

}
