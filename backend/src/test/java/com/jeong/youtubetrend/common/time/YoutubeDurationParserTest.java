package com.jeong.youtubetrend.common.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class YoutubeDurationParserTest {
    private final YoutubeDurationParser parser = new YoutubeDurationParser();

    @Test
    void parseMinutesAndSeconds() {
        assertEquals(95, parser.parseToSecond("PT1M35S"));
    }

    @Test
    void parseSecondsOnly() {
        assertEquals(45, parser.parseToSecond("PT45S"));
    }

    @Test
    void parseHoursMinutesSeconds() {
        assertEquals(3930, parser.parseToSecond("PT1H5M30S"));
    }
}
