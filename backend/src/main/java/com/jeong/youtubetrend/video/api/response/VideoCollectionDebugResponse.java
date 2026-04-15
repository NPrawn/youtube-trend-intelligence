package com.jeong.youtubetrend.video.api.response;

public record VideoCollectionDebugResponse(
        String regionCode,
        int requestedCount,
        int savedCount
) {
}
