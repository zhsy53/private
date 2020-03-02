package com.zch.persistence;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;

@MappedSuperclass
@Data
public abstract class IdEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, columnDefinition = "BIGINT UNSIGNED")
    @Id
    @PositiveOrZero
    protected long id;
}
