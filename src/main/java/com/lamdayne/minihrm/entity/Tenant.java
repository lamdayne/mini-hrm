package com.lamdayne.minihrm.entity;

import com.lamdayne.minihrm.enums.TenantStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tenants")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tenant extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, length = 20)
    private String taxCode;

    @Column(nullable = false, unique = true)
    private String representativeEmail;

    @Column(nullable = false, length = 20)
    private String phone;

    @Builder.Default
    @Enumerated(EnumType.STRING) // dùng tên enum
    @JdbcTypeCode(SqlTypes.NAMED_ENUM) //
    @Column(columnDefinition = "tenant_status") // cột này trong db kiểu tenant_status
    private TenantStatus status = TenantStatus.PENDING;

    @Builder.Default
    private BigDecimal standardHoursPerDay = BigDecimal.valueOf(8);

    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY)
    private List<Department> departments;

    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY)
    private List<Position> positions;

    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY)
    private List<Employee> employees;

    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY)
    private List<User> users;

}
