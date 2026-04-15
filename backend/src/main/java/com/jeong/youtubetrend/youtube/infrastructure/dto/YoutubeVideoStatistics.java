package com.jeong.youtubetrend.youtube.infrastructure.dto;

public record YoutubeVideoStatistics(
        String viewCount,
        String likeCount,
        String commentCount
) {
}
