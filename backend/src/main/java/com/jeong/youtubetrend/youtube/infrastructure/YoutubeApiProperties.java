package com.jeong.youtubetrend.youtube.infrastructure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "youtube.api")
public record YoutubeApiProperties(
        String key,
        String baseUrl
) {
}
