package com.demo.PoS.model.entity;

import jakarta.persistence.*;
import lombok.Setter;

import java.util.Set;

@Entity
@Setter
public class Employee extends User {
    @ManyToMany
    @JoinTable(
            name = "employee_providedService",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "providedService_id")
    )
    private Set<ProvidedService> providedServices;

}
