package com.jeong.youtubetrend.infra.youtube.dto;

import java.util.List;

public record YoutubeVideosResponse(
        List<YoutubeVideoItem> items
) {

}
