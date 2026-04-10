package com.jeong.youtubetrend.infra.youtube.dto;

public record YoutubeVideoItem(
        String id,
        YoutubeVideoSnippet snippet,
        YoutubeVideoStatistics statistics,
        YoutubeVideoContentDetails contentDetails
) {

}
