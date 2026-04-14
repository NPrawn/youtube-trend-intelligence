package com.jeong.youtubetrend.common.time;

import com.jeong.youtubetrend.common.exception.InvalidYoutubeResponseException;
import java.time.Duration;

import org.springframework.stereotype.Component;

@Component
public class YoutubeDurationParser {

    public int parseToSeconds(String isoDuration) {
        if (isoDuration == null || isoDuration.isBlank()) {
            throw new InvalidYoutubeResponseException("YouTube duration must not be blank.");
        }

        try {
            return Math.toIntExact(Duration.parse(isoDuration).getSeconds());
        } catch (RuntimeException exception) {
            throw new InvalidYoutubeResponseException(
                    "Failed to parse YouTube duration: " + isoDuration,
                    exception
            );
        }
    }
}
