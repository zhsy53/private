package com.zch.autoconfigure.jpush;

import cn.jiguang.common.ClientConfig;
import cn.jpush.api.JPushClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(JpushConfiguration.class)
@ConditionalOnClass(JPushClient.class)
public class JpushAutoConfiguration {
    @Bean
    JPushClient getClient(JpushConfiguration configuration) {
        return new JPushClient(configuration.getMasterSecret(), configuration.getAppKey(), null, ClientConfig.getInstance());
    }
}
