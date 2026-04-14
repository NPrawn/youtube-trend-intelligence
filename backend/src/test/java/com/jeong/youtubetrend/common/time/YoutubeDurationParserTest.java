package com.jeong.youtubetrend.common.time;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jeong.youtubetrend.common.exception.InvalidYoutubeResponseException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class YoutubeDurationParserTest {

    private final YoutubeDurationParser parser = new YoutubeDurationParser();

    @Test
    @DisplayName("분과 초로 구성된 duration을 초로 변환한다")
    void parsesMinutesAndSeconds() {
        assertThat(parser.parseToSeconds("PT1M35S")).isEqualTo(95);
    }

    @Test
    @DisplayName("초만 있는 duration을 초로 변환한다")
    void parsesSecondsOnly() {
        assertThat(parser.parseToSeconds("PT45S")).isEqualTo(45);
    }

    @Test
    @DisplayName("시 분 초로 구성된 duration을 초로 변환한다")
    void parsesHoursMinutesSeconds() {
        assertThat(parser.parseToSeconds("PT1H5M30S")).isEqualTo(3930);
    }

    @Test
    @DisplayName("비어 있는 duration이 들어오면 예외가 발생한다")
    void throwsExceptionWhenDurationIsBlank() {
        assertThatThrownBy(() -> parser.parseToSeconds(" "))
                .isInstanceOf(InvalidYoutubeResponseException.class)
                .hasMessage("YouTube duration must not be blank.");
    }

    @Test
    @DisplayName("잘못된 duration 형식이 들어오면 예외가 발생한다")
    void throwsExceptionWhenDurationFormatIsInvalid() {
        assertThatThrownBy(() -> parser.parseToSeconds("invalid-duration"))
                .isInstanceOf(InvalidYoutubeResponseException.class)
                .hasMessageContaining("Failed to parse YouTube duration");
    }
}
