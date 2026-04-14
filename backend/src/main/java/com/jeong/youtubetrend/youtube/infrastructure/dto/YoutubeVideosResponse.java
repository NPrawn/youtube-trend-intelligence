package com.jeong.youtubetrend.youtube.infrastructure.dto;

import java.util.List;

public record YoutubeVideosResponse(
        List<YoutubeVideoItem> items
) {
}
