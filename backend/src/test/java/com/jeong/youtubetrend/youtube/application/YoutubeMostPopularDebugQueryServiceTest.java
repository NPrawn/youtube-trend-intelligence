package com.jeong.youtubetrend.youtube.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.jeong.youtubetrend.youtube.api.response.YoutubeMostPopularDebugResponse;
import com.jeong.youtubetrend.youtube.infrastructure.YoutubeVideoClient;
import com.jeong.youtubetrend.youtube.infrastructure.dto.YoutubeVideoContentDetails;
import com.jeong.youtubetrend.youtube.infrastructure.dto.YoutubeVideoItem;
import com.jeong.youtubetrend.youtube.infrastructure.dto.YoutubeVideoSnippet;
import com.jeong.youtubetrend.youtube.infrastructure.dto.YoutubeVideoStatistics;
import com.jeong.youtubetrend.youtube.infrastructure.dto.YoutubeVideosResponse;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class YoutubeMostPopularDebugQueryServiceTest {

    private final YoutubeVideoClient youtubeVideoClient = Mockito.mock(YoutubeVideoClient.class);
    private final YoutubeMostPopularDebugQueryService youtubeMostPopularDebugQueryService =
            new YoutubeMostPopularDebugQueryService(youtubeVideoClient);

    @Test
    @DisplayName("디버그 조회 응답을 내부 API 응답 모델로 변환한다")
    void fetchesMostPopularVideosAsInternalResponse() {
        // given
        YoutubeVideosResponse youtubeVideosResponse = new YoutubeVideosResponse(
                List.of(
                        new YoutubeVideoItem(
                                "video-1",
                                new YoutubeVideoSnippet(
                                        "Sample title",
                                        "channel-1",
                                        "2026-04-14T00:00:00Z"
                                ),
                                new YoutubeVideoStatistics(
                                        "1000",
                                        "100",
                                        "10"
                                ),
                                new YoutubeVideoContentDetails("PT5M10S")
                        )
                )
        );

        given(youtubeVideoClient.fetchMostPopularVideos("KR", 5)).willReturn(youtubeVideosResponse);

        // when
        YoutubeMostPopularDebugResponse response = youtubeMostPopularDebugQueryService.fetchMostPopular("KR", 5);

        // then
        assertThat(response.items()).hasSize(1);
        assertThat(response.items().get(0).videoId()).isEqualTo("video-1");
        assertThat(response.items().get(0).title()).isEqualTo("Sample title");
        assertThat(response.items().get(0).channelId()).isEqualTo("channel-1");
        assertThat(response.items().get(0).duration()).isEqualTo("PT5M10S");
        assertThat(response.items().get(0).viewCount()).isEqualTo("1000");
        assertThat(response.items().get(0).likeCount()).isEqualTo("100");
        assertThat(response.items().get(0).commentCount()).isEqualTo("10");
    }
}
