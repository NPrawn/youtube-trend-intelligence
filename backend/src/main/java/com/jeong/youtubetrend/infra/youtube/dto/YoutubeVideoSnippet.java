package com.jeong.youtubetrend.infra.youtube.dto;

public record YoutubeVideoSnippet(
        String title,
        String channelId,
        String publishedAt
) {

}
