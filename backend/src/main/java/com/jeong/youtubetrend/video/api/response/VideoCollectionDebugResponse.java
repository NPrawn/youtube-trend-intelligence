package com.jeong.youtubetrend.video.api.response;

public record VideoCollectionDebugResponse(
        String regionCode,
        String videoCategoryId,
        int requestedCount,
        int savedCount
) {
}
