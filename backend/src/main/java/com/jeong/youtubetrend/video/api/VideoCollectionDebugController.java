package com.jeong.youtubetrend.video.api;



import com.jeong.youtubetrend.video.api.response.VideoCollectionDebugResponse;
import com.jeong.youtubetrend.video.application.VideoCollectionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VideoCollectionDebugController {

    private final VideoCollectionService videoCollectionService;

    public VideoCollectionDebugController(VideoCollectionService videoCollectionService) {
        this.videoCollectionService = videoCollectionService;
    }

    @GetMapping("/debug/videos/collect-most-popular")
    public VideoCollectionDebugResponse collectMostPopular(
            @RequestParam(defaultValue = "KR") String regionCode,
            @RequestParam(defaultValue = "10") int maxResults
    ) {
        int savedCount = videoCollectionService.collectMostPopularVideos(regionCode, maxResults);

        return new VideoCollectionDebugResponse(
                regionCode,
                maxResults,
                savedCount
        );
    }
}
