package com.jeong.youtubetrend.video.api;



import com.jeong.youtubetrend.video.api.response.VideoCollectionDebugResponse;
import com.jeong.youtubetrend.video.application.VideoCollectionService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class VideoCollectionDebugController {

    private final VideoCollectionService videoCollectionService;

    public VideoCollectionDebugController(VideoCollectionService videoCollectionService) {
        this.videoCollectionService = videoCollectionService;
    }

    @GetMapping("/debug/videos/collect-most-popular")
    public VideoCollectionDebugResponse collectMostPopular(
            @RequestParam(defaultValue = "KR")
            @Pattern(regexp = "^[A-Z]{2}$", message = "regionCode must be a 2-letter uppercase country code.")
            String regionCode,
            @RequestParam(defaultValue = "10")
            @Min(value = 1, message = "maxResults must be at least 1.")
            @Max(value = 50, message = "maxResults must be at most 50.")
            int maxResults,
            @RequestParam(required = false)
            @Pattern(regexp = "^\\d+$", message = "videoCategoryId must contain digits only")
            String videoCategoryId
    ) {
        int savedCount = videoCollectionService.collectMostPopularVideos(regionCode, maxResults, videoCategoryId);

        return new VideoCollectionDebugResponse(
                regionCode,
                videoCategoryId,
                maxResults,
                savedCount
        );
    }
}
