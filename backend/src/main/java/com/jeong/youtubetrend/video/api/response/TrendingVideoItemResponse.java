package com.jeong.youtubetrend.video.api.response;

import java.time.OffsetDateTime;

public record TrendingVideoItemResponse (
        String videoId,
        String title,
        String channelId,
        OffsetDateTime publishedAt,
        int durationSeconds,
        boolean shortForm,
        long viewCount,
        Long likeCount,
        Long commentCount,
        String sourceRegion,
        String sourceCategory,
        Integer sourceRank,
        OffsetDateTime collectedAt
) {

}
