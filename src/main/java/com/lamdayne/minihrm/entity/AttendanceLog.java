package com.lamdayne.minihrm.entity;

import com.lamdayne.minihrm.enums.AttendanceStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "attendance_logs")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceLog extends BaseEntity {

    private LocalDate workDate;

    private Instant checkInTime;

    private Instant checkOutTime;

    private BigDecimal workedHours;

    private AttendanceStatus status;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

}
