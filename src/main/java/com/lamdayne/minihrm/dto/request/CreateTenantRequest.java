package com.lamdayne.minihrm.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class CreateTenantRequest implements Serializable {

    @NotBlank(message = "TENANT_NAME_REQUIRED")
    private String name;

    @Size(max = 20, message = "TENANT_TAX_CODE_INVALID")
    @NotBlank(message = "TENANT_TAX_CODE_REQUIRED")
    private String taxCode;

    @NotBlank(message = "TENANT_EMAIL_REQUIRED")
    private String representativeEmail;

    @Size(max = 20, message = "TENANT_PHONE_INVALID")
    @NotBlank(message = "TENANT_PHONE_REQUIRED")
    private String phone;
}
