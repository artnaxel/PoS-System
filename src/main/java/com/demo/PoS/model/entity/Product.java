package com.demo.PoS.model.entity;

import jakarta.persistence.Entity;
import lombok.Setter;

@Entity
@Setter
public class Product extends Item {

    private Integer stock;

}

