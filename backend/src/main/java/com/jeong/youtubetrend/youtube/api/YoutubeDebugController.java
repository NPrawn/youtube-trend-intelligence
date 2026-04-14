package com.jeong.youtubetrend.youtube.api;

import com.jeong.youtubetrend.youtube.api.response.YoutubeMostPopularDebugResponse;
import com.jeong.youtubetrend.youtube.application.YoutubeMostPopularDebugQueryService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class YoutubeDebugController {

    private final YoutubeMostPopularDebugQueryService youtubeMostPopularDebugQueryService;

    public YoutubeDebugController(YoutubeMostPopularDebugQueryService youtubeMostPopularDebugQueryService) {
        this.youtubeMostPopularDebugQueryService = youtubeMostPopularDebugQueryService;
    }

    @GetMapping("/debug/youtube/most-popular")
    public YoutubeMostPopularDebugResponse fetchMostPopular(
            @RequestParam(defaultValue = "KR") String regionCode,
            @RequestParam(defaultValue = "5") int maxResults
    ) {
        return youtubeMostPopularDebugQueryService.fetchMostPopular(regionCode, maxResults);
    }
}
