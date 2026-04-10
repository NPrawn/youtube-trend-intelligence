package com.jeong.youtubetrend.api.youtube;

import com.jeong.youtubetrend.infra.youtube.YoutubeVideoClient;
import com.jeong.youtubetrend.infra.youtube.dto.YoutubeVideosResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class YoutubeTestController {

    private final YoutubeVideoClient youtubeVideoClient;

    public YoutubeTestController(YoutubeVideoClient youtubeVideoClient) {
        this.youtubeVideoClient = youtubeVideoClient;
    }

    @GetMapping("/debug/youtube/most-popular")
    public YoutubeVideosResponse fetchMostPopular(
            @RequestParam(defaultValue = "KR") String regionCode,
            @RequestParam(defaultValue = "5") int maxResults
     ) {
        return youtubeVideoClient.fetchMostPopularVideos(regionCode, maxResults);
    }
}
