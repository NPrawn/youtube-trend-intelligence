package com.jeong.youtubetrend.infra.youtube.dto;

public record YoutubeVideoStatistics(
        String viewCount,
        String likeCount,
        String commentCount
) {

}
