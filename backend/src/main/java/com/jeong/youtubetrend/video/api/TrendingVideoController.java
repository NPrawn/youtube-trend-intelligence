package com.jeong.youtubetrend.video.api;

import com.jeong.youtubetrend.video.api.response.TrendingVideosResponse;
import com.jeong.youtubetrend.video.application.TrendingVideoQueryService;
import com.jeong.youtubetrend.video.application.VideoFormType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class TrendingVideoController {

    private final TrendingVideoQueryService trendingVideoQueryService;
    public TrendingVideoController(TrendingVideoQueryService trendingVideoQueryService) {
        this.trendingVideoQueryService = trendingVideoQueryService;
    }

    @GetMapping("/trends/videos")
    public TrendingVideosResponse findTrendingVideos(
            @RequestParam(defaultValue = "KR")
            @Pattern(regexp = "^[A-Z]{2}$", message = "region must be a 2-letter uppercase country code.")
            String region,
            @RequestParam(defaultValue = "all")
            String form,
            @RequestParam(defaultValue = "20")
            @Min(value = 1, message = "limit must be at least 1.")
            @Max(value = 50, message = "limit must be at most 50.")
            int limit
    ) {
        return trendingVideoQueryService.findTrendingVideos(
                region,
                VideoFormType.from(form),
                limit
        );
    }
}
