package com.jeong.youtubetrend.video.api.response;

import java.util.List;

public record TrendingVideosResponse(
        List<TrendingVideoItemResponse> items
) {

}
