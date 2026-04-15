package com.jeong.youtubetrend.youtube.api.response;

public record YoutubeMostPopularDebugItemResponse(
        String videoId,
        String title,
        String channelId,
        String publishedAt,
        String duration,
        String viewCount,
        String likeCount,
        String commentCount
) {
}
