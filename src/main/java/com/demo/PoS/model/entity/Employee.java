package com.demo.PoS.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.cglib.util.ParallelSorter;

import java.util.Set;

@Entity
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends User {
    @ManyToMany
    @JoinTable(
            name = "employee_providedService",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "providedService_id")
    )
    private Set<ProvidedService> providedServices;

    @Builder.Default
    private PosTimestamps timestamps = new PosTimestamps();

}
