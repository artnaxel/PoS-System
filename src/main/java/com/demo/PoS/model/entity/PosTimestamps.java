package com.demo.PoS.model.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@Embeddable
@Slf4j
public class PosTimestamps {
    private Timestamp createdAt;
    private Timestamp modifiedAt;

    @PrePersist
    public void prePersist() {
        log.info("Writing new object to database - adding timestamps.");
        this.createdAt = this.modifiedAt = Timestamp.from(Instant.now());
    }
    @PreUpdate
    public void preUpdate() {
        log.info("Updating object modified timestamp.");
        this.modifiedAt = Timestamp.from(Instant.now());
    }
}
