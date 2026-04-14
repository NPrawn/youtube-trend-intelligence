package com.jeong.youtubetrend.youtube.infrastructure.dto;

public record YoutubeVideoSnippet(
        String title,
        String channelId,
        String publishedAt
) {
}
