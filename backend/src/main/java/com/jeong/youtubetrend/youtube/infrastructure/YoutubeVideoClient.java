package com.jeong.youtubetrend.youtube.infrastructure;

import com.jeong.youtubetrend.common.exception.ExternalApiException;
import com.jeong.youtubetrend.youtube.infrastructure.dto.YoutubeVideosResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
public class YoutubeVideoClient {

    private static final Logger log = LoggerFactory.getLogger(YoutubeVideoClient.class);
    private static final String VIDEO_PARTS = "snippet,statistics,contentDetails";

    private final RestClient restClient;
    private final YoutubeApiProperties properties;

    public YoutubeVideoClient(RestClient restClient, YoutubeApiProperties properties) {
        this.restClient = restClient;
        this.properties = properties;
    }

    public YoutubeVideosResponse fetchMostPopularVideos(String regionCode, int maxResults) {
        return fetchMostPopularVideos(regionCode, maxResults, null);
    }

    public YoutubeVideosResponse fetchMostPopularVideos(String regionCode, int maxResults, String videoCategoryId) {
        try {
            return restClient.get()
                    .uri(uriBuilder -> {
                        var builder = uriBuilder
                                .path("/video")
                                .queryParam("part", VIDEO_PARTS)
                                .queryParam("chart", "mostPopular")
                                .queryParam("regionCode", regionCode)
                                .queryParam("maxResults", maxResults);

                        if (videoCategoryId != null && !videoCategoryId.isBlank()) {
                            builder.queryParam("videoCategoryId", videoCategoryId);
                        }

                        return builder
                                .queryParam("key", properties.key())
                                .build();
                    })
                    .retrieve()
                    .body(YoutubeVideosResponse.class);
        } catch (RestClientException exception) {
            log.error(
                    "Failed to fetch Youtube most popular videos. regionCode={}, maxResults={}, videoCategoryId={}",
                    regionCode,
                    maxResults,
                    videoCategoryId,
                    exception
            );
            throw new ExternalApiException("Failed to fetch YouTube most popular videos.", exception);
        }
    }
}
