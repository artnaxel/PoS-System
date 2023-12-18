package com.demo.PoS.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.util.Set;

@Entity
public class ProvidedService extends Item {

    @ManyToMany(mappedBy = "providedServices")
    private Set<Employee> employees;
}
