package com.demo.PoS.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class EmployeeDetails {

    private UUID id;
    private String name;
    private String surname;
    private Set<UUID> serviceIds;

}
