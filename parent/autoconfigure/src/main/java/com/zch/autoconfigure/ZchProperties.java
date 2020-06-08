package com.zch.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@ConfigurationProperties(prefix = "zch")
@Data
public class ZchProperties {
    @NotNull
    private Token token = new Token();

    @NotNull
    private Log log = new Log();

    @NotNull
    private Cache cache = new Cache();

    @NotNull
    private Swagger swagger = new Swagger();

    @NotNull
    private JPush jPush = new JPush();

    @NotNull
    private Oss oss = new Oss();

    @NotNull
    private Enabled enabled = new Enabled();

    @Data
    public static class Token {
        @NotNull
        private Jwt jwt = new Jwt();

        @Data
        public static class Jwt {
            @NotBlank
            private String key = "zch2020";
            @Positive
            private long expirationMinutes = 60 * 24 * 30;
        }
    }

    @Data
    public static class Log {
        private boolean debug = false;
    }

    @Data
    public static class Cache {
        @Positive
        private long expirationMinutes = 60 * 12;
    }

    @Data
    public static class Swagger {
        private boolean enabled;
        @NotBlank
        private String title;
        @Nullable
        private String description;
        @Nullable
        private String path;
        @NotBlank
        private String version = "1.0";
    }

    @Data
    public static class JPush {
        @NotBlank
        private String masterSecret;
        @NotBlank
        private String appKey;
        private boolean production;
    }

    @Data
    public static class Enabled {
        private boolean jackson = true;
        private boolean convert = true;
        private boolean exception = true;
    }

    @Data
    public static class Oss {
        @NotBlank
        private String url;
        @NotBlank
        private String bucket;
        @NotBlank
        private String key;
        @NotBlank
        private String secret;
    }
}
