package com.zch.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@ConfigurationProperties(prefix = "zch")
@Data
public class ZchProperties {
    @NotNull
    private Token token = new Token();

    @NotNull
    private Enabled enabled = new Enabled();

    @NotNull
    private Log log = new Log();

    @Data
    public static class Enabled {
        private boolean jackson = true;
        private boolean convert = true;
        private boolean exception = true;
    }

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
}
