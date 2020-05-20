package com.zch.autoconfigure.jpush;

import lombok.Data;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotBlank;

@EnableAutoConfiguration
@ConfigurationProperties(prefix = "jpush")
@Data
public class JpushConfiguration {
    @NotBlank
    private String masterSecret;
    @NotBlank
    private String appKey;
    private boolean production;
}
