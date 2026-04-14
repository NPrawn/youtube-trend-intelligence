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

    private final RestClient restClient;
    private final YoutubeApiProperties properties;

    public YoutubeVideoClient(RestClient restClient, YoutubeApiProperties properties) {
        this.restClient = restClient;
        this.properties = properties;
    }

    public YoutubeVideosResponse fetchMostPopularVideos(String regionCode, int maxResults) {
        try {
            return restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/videos")
                            .queryParam("part", "snippet,statistics,contentDetails")
                            .queryParam("chart", "mostPopular")
                            .queryParam("regionCode", regionCode)
                            .queryParam("key", properties.key())
                            .build()
                    )
                    .retrieve()
                    .body(YoutubeVideosResponse.class);
        } catch (RestClientException exception) {
            log.error("Failed to fetch YouTube most popular videos. regionCode={}, maxResults={}", regionCode, maxResults, exception);
            throw new ExternalApiException("Failed to fetch YouTube most popular videos.", exception);
        }
    }
}
