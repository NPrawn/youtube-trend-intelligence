package com.jeong.youtubetrend.infra.youtube.dto;

import java.util.List;

public record YoutubeVideoResponse(
        List<YoutubeVideoItem> items
) {

}
