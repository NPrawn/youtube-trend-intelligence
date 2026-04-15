package com.jeong.youtubetrend.youtube.infrastructure.dto;

public record YoutubeVideoItem(
        String id,
        YoutubeVideoSnippet snippet,
        YoutubeVideoStatistics statistics,
        YoutubeVideoContentDetails contentDetails
) {
}
