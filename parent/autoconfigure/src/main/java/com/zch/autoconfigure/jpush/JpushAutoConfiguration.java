package com.zch.autoconfigure.jpush;

import cn.jiguang.common.ClientConfig;
import cn.jpush.api.JPushClient;
import com.zch.autoconfigure.ZchProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(ZchProperties.class)
@ConditionalOnClass(JPushClient.class)
public class JpushAutoConfiguration {
    @Bean
    JPushClient getClient(ZchProperties zchProperties) {
        var jPush = zchProperties.getJPush();
        return new JPushClient(jPush.getMasterSecret(), jPush.getAppKey(), null, ClientConfig.getInstance());
    }
}
