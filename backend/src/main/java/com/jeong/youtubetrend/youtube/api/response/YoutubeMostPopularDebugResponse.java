package com.jeong.youtubetrend.youtube.api.response;

import java.util.List;

public record YoutubeMostPopularDebugResponse(
        List<YoutubeMostPopularDebugItemResponse> items
) {
}
