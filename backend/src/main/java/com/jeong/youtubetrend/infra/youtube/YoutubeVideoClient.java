package com.jeong.youtubetrend.infra.youtube;

import com.jeong.youtubetrend.infra.youtube.dto.YoutubeVideosResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class YoutubeVideoClient {

    private final RestClient restClient;
    private final YoutubeApiProperties properties;

    public YoutubeVideoClient(RestClient restClient, YoutubeApiProperties properties) {
        this.restClient = restClient;
        this.properties = properties;
    }

    public YoutubeVideosResponse fetchMostPopularVideos(String regionCode, int maxResults) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/videos")
                        .queryParam("part", "snippet,statistices,contentDetails")
                        .queryParam("chart", "mostPopular")
                        .queryParam("regionCode", regionCode)
                        .queryParam("key", properties.key())
                        .build()
                )
                .retrieve()
                .body(YoutubeVideosResponse.class);
    }
}
