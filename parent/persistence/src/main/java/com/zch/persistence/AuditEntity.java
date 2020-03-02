package com.zch.persistence;

import lombok.Data;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
public class AuditEntity extends BaseEntity {
    @Column(nullable = false, updatable = false, columnDefinition = "BIGINT UNSIGNED")
    @Positive
    protected long creatorId;

    @Column(insertable = false, columnDefinition = "DATETIME(0)")
    @Nullable
    protected LocalDateTime auditTime;

    @Column(insertable = false, columnDefinition = "BIGINT UNSIGNED DEFAULT 0")
    @PositiveOrZero
    protected long auditorId;
}
