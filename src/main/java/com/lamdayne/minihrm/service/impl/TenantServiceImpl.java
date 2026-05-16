package com.lamdayne.minihrm.service.impl;

import com.lamdayne.minihrm.dto.request.CreateTenantRequest;
import com.lamdayne.minihrm.dto.response.TenantResponse;
import com.lamdayne.minihrm.entity.Tenant;
import com.lamdayne.minihrm.entity.User;
import com.lamdayne.minihrm.enums.UserRole;
import com.lamdayne.minihrm.exception.AppException;
import com.lamdayne.minihrm.exception.ErrorCode;
import com.lamdayne.minihrm.mapper.TenantMapper;
import com.lamdayne.minihrm.repository.TenantRepository;
import com.lamdayne.minihrm.repository.UserRepository;
import com.lamdayne.minihrm.service.MailService;
import com.lamdayne.minihrm.service.TenantService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;
    private final TenantMapper tenantMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    @Override
    @Transactional
    public TenantResponse createTenant(CreateTenantRequest request) throws MessagingException, UnsupportedEncodingException {
        if (tenantRepository.existsByTaxCode(request.getTaxCode()))
            throw new AppException(ErrorCode.TENANT_TAX_CODE_EXISTS);
        if (tenantRepository.existsByRepresentativeEmail(request.getRepresentativeEmail()))
            throw new AppException(ErrorCode.TENANT_EMAIL_EXISTS);

        Tenant tenant = tenantMapper.toTenant(request);
        tenant = tenantRepository.save(tenant);

        // create company admin
        String tempPassword = generateTempPassword(12);
        User user = User.builder()
                .email(tenant.getRepresentativeEmail())
                .password(passwordEncoder.encode(tempPassword))
                .role(UserRole.COMPANY_ADMIN)
                .active(true)
                .tenant(tenant)
                .build();

        userRepository.save(user);

        mailService.sendTempPassword(tenant.getRepresentativeEmail(), tempPassword);

        return tenantMapper.toTenantResponse(tenant);
    }

    private String generateTempPassword(int length) {
        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            result.append(CHARACTERS.charAt(index));
        }
        return result.toString();
    }

}
