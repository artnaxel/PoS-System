package com.demo.PoS.model.entity;

import com.demo.PoS.model.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "pos_user")
public class User {
    @Id
    @GeneratedValue
    private UUID id;

    private String username;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;
}
