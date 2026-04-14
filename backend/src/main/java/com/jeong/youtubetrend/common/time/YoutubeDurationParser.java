package com.jeong.youtubetrend.common.time;

import java.time.Duration;
import org.springframework.stereotype.Component;

@Component
public class YoutubeDurationParser {
    public int parseToSecond(String isoDuration) {
        if (isoDuration == null || isoDuration.isBlank()) {
            throw new IllegalArgumentException("isoDuration must not be blank");
        }

        return Math.toIntExact(Duration.parse(isoDuration).getSeconds());
    }
}
