package com.lamdayne.minihrm.service;

import com.lamdayne.minihrm.dto.request.CreateTenantRequest;
import com.lamdayne.minihrm.dto.response.TenantResponse;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface TenantService {
    TenantResponse createTenant(CreateTenantRequest request) throws MessagingException, UnsupportedEncodingException;
}
