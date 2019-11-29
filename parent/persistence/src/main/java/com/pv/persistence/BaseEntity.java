package com.pv.persistence;

import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
public abstract class BaseEntity extends SimpleEntity {
    @UpdateTimestamp
    @Column(columnDefinition = "DATETIME(0)")
    @Nullable
    protected LocalDateTime updateTime;

    @Version
    @Column(nullable = false, columnDefinition = "BIGINT UNSIGNED")
    @Positive
    protected long version = 1;
}
