package com.zch.autoconfigure.jpush;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotBlank;

@ConfigurationProperties(prefix = "jpush")
@Data
public class JpushConfiguration {
    @NotBlank
    private String masterSecret;
    @NotBlank
    private String appKey;
    private boolean production;
}
