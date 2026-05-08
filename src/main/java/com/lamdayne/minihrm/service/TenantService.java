package com.lamdayne.minihrm.service;

import com.lamdayne.minihrm.dto.request.CreateTenantRequest;
import com.lamdayne.minihrm.dto.response.TenantResponse;

public interface TenantService {
    TenantResponse createTenant(CreateTenantRequest request);
}
