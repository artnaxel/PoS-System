package com.demo.PoS.model.entity;

import jakarta.persistence.*;
import lombok.Setter;

import java.util.UUID;

@MappedSuperclass
@Setter
public class User {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String surname;
}
