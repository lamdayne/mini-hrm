package com.lamdayne.minihrm.service.impl;

import com.lamdayne.minihrm.dto.request.CreateTenantRequest;
import com.lamdayne.minihrm.dto.response.TenantResponse;
import com.lamdayne.minihrm.entity.Tenant;
import com.lamdayne.minihrm.exception.AppException;
import com.lamdayne.minihrm.exception.ErrorCode;
import com.lamdayne.minihrm.mapper.TenantMapper;
import com.lamdayne.minihrm.repository.TenantRepository;
import com.lamdayne.minihrm.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;
    private final TenantMapper tenantMapper;

    @Override
    public TenantResponse createTenant(CreateTenantRequest request) {
        if (tenantRepository.existsByTaxCode(request.getTaxCode())) {
            throw new AppException(ErrorCode.TENANT_TAX_CODE_EXISTS);
        }

        if (tenantRepository.existsByRepresentativeEmail(request.getRepresentativeEmail())) {
            throw new AppException(ErrorCode.TENANT_EMAIL_EXISTS);
        }

        Tenant tenant = tenantMapper.toTenant(request);
        return tenantMapper.toTenantResponse(tenantRepository.save(tenant));
    }
}
