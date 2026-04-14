package com.jeong.youtubetrend.youtube.application;

import com.jeong.youtubetrend.youtube.api.response.YoutubeMostPopularDebugItemResponse;
import com.jeong.youtubetrend.youtube.api.response.YoutubeMostPopularDebugResponse;
import com.jeong.youtubetrend.youtube.infrastructure.YoutubeVideoClient;
import com.jeong.youtubetrend.youtube.infrastructure.dto.YoutubeVideoItem;
import com.jeong.youtubetrend.youtube.infrastructure.dto.YoutubeVideosResponse;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class YoutubeMostPopularDebugQueryService {

    private final YoutubeVideoClient youtubeVideoClient;

    public YoutubeMostPopularDebugQueryService(YoutubeVideoClient youtubeVideoClient) {
        this.youtubeVideoClient = youtubeVideoClient;
    }

    public YoutubeMostPopularDebugResponse fetchMostPopular(String regionCode, int maxResults) {
        YoutubeVideosResponse response = youtubeVideoClient.fetchMostPopularVideos(regionCode, maxResults);

        return new YoutubeMostPopularDebugResponse(
                response.items().stream()
                        .map(this::toResponse)
                        .toList()
        );
    }

    private YoutubeMostPopularDebugItemResponse toResponse(YoutubeVideoItem item) {
        return new YoutubeMostPopularDebugItemResponse(
                item.id(),
                item.snippet().title(),
                item.snippet().channelId(),
                item.snippet().publishedAt(),
                item.contentDetails().duration(),
                item.statistics().viewCount(),
                item.statistics().likeCount(),
                item.statistics().commentCount()
        );
    }
}
