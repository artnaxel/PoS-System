package com.demo.PoS.models;

import jakarta.persistence.*;
import lombok.Getter;

@Embeddable
public record Example(
        @Column(nullable = false, length = 10)
        String code,
        String desc
){
        @Entity
        @Getter
        public static class ExampleModel {
                @GeneratedValue
                @Id
                Long id;
                @Embedded
                Example example;
        }
}

