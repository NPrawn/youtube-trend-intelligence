package com.jeong.youtubetrend.youtube.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class YoutubeApiConfig {

    @Bean
    public RestClient youtubeRestClient(YoutubeApiProperties properties) {
        return RestClient.builder()
                .baseUrl(properties.baseUrl())
                .build();
    }
}
