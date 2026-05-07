package com.lamdayne.minihrm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "positions")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Position extends BaseEntity {

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @OneToMany(mappedBy = "position")
    private List<Employee> employees;

}
