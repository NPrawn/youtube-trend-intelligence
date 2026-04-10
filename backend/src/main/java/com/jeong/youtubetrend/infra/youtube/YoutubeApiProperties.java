package com.jeong.youtubetrend.infra.youtube;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "youtube.api")
public record YoutubeApiProperties(
        String key,
        String baseUrl
) {
}
