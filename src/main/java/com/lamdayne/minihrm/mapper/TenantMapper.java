package com.lamdayne.minihrm.mapper;

import com.lamdayne.minihrm.dto.request.CreateTenantRequest;
import com.lamdayne.minihrm.dto.response.TenantResponse;
import com.lamdayne.minihrm.entity.Tenant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TenantMapper {
    Tenant toTenant(CreateTenantRequest request);

    TenantResponse toTenantResponse(Tenant tenant);
}
