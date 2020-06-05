package com.zch.autoconfigure.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotBlank;

@ConfigurationProperties(prefix = "zch.oss")
@Data
public class OssProperties {
    @NotBlank
    private String url;
    @NotBlank
    private String bucket;
    @NotBlank
    private String key;
    @NotBlank
    private String secret;
}
