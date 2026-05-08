package com.lamdayne.minihrm.dto.response;

import com.lamdayne.minihrm.enums.TenantStatus;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class TenantResponse implements Serializable {
    private Long id;
    private String name;
    private String taxCode;
    private String representativeEmail;
    private String phone;
    private TenantStatus status;
    private BigDecimal standardHoursPerDay;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;
}
