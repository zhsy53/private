package com.zch.persistence;

import com.zch.persistence.generator.SnowflakeGenerator;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
public abstract class SimpleEntity {
    @GenericGenerator(name = SnowflakeGenerator.NAME, strategy = SnowflakeGenerator.CLASS)
    @GeneratedValue(generator = SnowflakeGenerator.NAME)
    @Column(nullable = false, updatable = false, columnDefinition = "BIGINT UNSIGNED")
    @Id
    @Positive
    protected long id;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, columnDefinition = "DATETIME(0)")
    @NotNull
    protected LocalDateTime createTime = LocalDateTime.now();
}
