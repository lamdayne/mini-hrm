package com.lamdayne.minihrm.entity;

import com.lamdayne.minihrm.enums.EmployeeStatus;
import com.lamdayne.minihrm.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "employees")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee extends BaseEntity {

    private String employeeCode;

    private String fullName;

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String email;

    private String phone;

    private String address;

    private LocalDate startDate;

    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    @OneToMany(mappedBy = "employee")
    private List<AttendanceLog> attendanceLogs;

}
