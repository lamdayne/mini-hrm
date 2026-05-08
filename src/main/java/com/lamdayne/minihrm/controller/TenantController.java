package com.lamdayne.minihrm.controller;

import com.lamdayne.minihrm.dto.request.CreateTenantRequest;
import com.lamdayne.minihrm.dto.response.ApiResponse;
import com.lamdayne.minihrm.dto.response.TenantResponse;
import com.lamdayne.minihrm.service.TenantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;

    @PostMapping
    public ApiResponse<TenantResponse> createTenant(@RequestBody @Valid CreateTenantRequest request) {
        return ApiResponse.<TenantResponse>builder()
                .message("Create tenant successfully!")
                .data(tenantService.createTenant(request))
                .build();
    }

}
