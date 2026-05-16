package com.lamdayne.minihrm.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ErrorCode {
    INVALID_ERROR_CODE(1001, "Invalid key", HttpStatus.BAD_REQUEST),
    TENANT_NAME_REQUIRED(2001, "Tenant name can not blank", HttpStatus.BAD_REQUEST),
    TENANT_TAX_CODE_REQUIRED(2002, "Tenant tax code can not blank", HttpStatus.BAD_REQUEST),
    TENANT_TAX_CODE_INVALID(2003, "Tenant tax code must be less than 20 character", HttpStatus.BAD_REQUEST),
    TENANT_EMAIL_REQUIRED(2004, "Tenant email can not blank", HttpStatus.BAD_REQUEST),
    TENANT_PHONE_REQUIRED(2005, "Tenant phone can not blank", HttpStatus.BAD_REQUEST),
    TENANT_PHONE_INVALID(2006, "Tenant phone must be less than 20 character", HttpStatus.BAD_REQUEST),
    TENANT_TAX_CODE_EXISTS(2007, "Tenant tax code already exists", HttpStatus.CONFLICT),
    TENANT_EMAIL_EXISTS(2008, "Tenant email already exists", HttpStatus.CONFLICT),
    ;

    private int code;
    private String message;
    private HttpStatus httpStatus;

}
