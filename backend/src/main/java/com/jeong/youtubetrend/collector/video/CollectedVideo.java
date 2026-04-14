package com.jeong.youtubetrend.collector.video;

import java.time.OffsetDateTime;

public record CollectedVideo(
        String youtubeVideoId,
        String title,
        String youtubeChannelId,
        OffsetDateTime publishedAt,
        int durationSeconds,
        boolean shortForm,
        long viewCount,
        Long likeCount,
        Long commentCount
) {
}
