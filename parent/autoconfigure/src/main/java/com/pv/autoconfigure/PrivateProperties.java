package com.pv.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@ConfigurationProperties(prefix = "pv")
@Data
public class PrivateProperties {
    @NotNull
    private Token token = new Token();

    @NotNull
    private Enabled enabled = new Enabled();

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
            private String key = "pv2019";
            @Positive
            private long expirationMinutes = 60 * 24;
        }
    }
}
