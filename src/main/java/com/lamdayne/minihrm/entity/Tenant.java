package com.lamdayne.minihrm.entity;

import com.lamdayne.minihrm.enums.TenantStatus;
import jakarta.persistence.*;
import lombok.*;

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

    private String name;

    private String taxCode;

    private String representativeEmail;

    private String phone;

    @Enumerated(EnumType.STRING)
    private TenantStatus status;

    private BigDecimal standardHoursPerDay;

    @OneToMany(mappedBy = "tenant")
    private List<Department> departments;

    @OneToMany(mappedBy = "tenant")
    private List<Position> positions;

    @OneToMany(mappedBy = "tenant")
    private List<Employee> employees;

    @OneToMany(mappedBy = "tenant")
    private List<User> users;

}
