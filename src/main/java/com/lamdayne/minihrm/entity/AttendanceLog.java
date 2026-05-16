package com.lamdayne.minihrm.entity;

import com.lamdayne.minihrm.enums.AttendanceStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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

    @Column(nullable = false)
    private LocalDate workDate; // ngày làm việc theo Asia/Ho_Chi_Minh (BR-003)

    @Column(nullable = false)
    private Instant checkInTime;

    private Instant checkOutTime;

    @Builder.Default
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal workedHours = BigDecimal.ZERO;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(columnDefinition = "attendance_status", nullable = false)
    private AttendanceStatus status = AttendanceStatus.CHECKED_IN;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

}
