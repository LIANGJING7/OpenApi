package com.jing.jingclientsdk;

import com.jing.jingclientsdk.client.JingApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("jingapi.client")
@Data
@ComponentScan
public class JIngApiClientConfig {

    private String accessKey;
    private String secretKey;

    @Bean
    public JingApiClient ttt() {
        return new JingApiClient(accessKey, secretKey);
    }
}
