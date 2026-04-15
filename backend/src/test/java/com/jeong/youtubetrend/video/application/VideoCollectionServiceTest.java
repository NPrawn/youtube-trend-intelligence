package com.jeong.youtubetrend.video.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.jeong.youtubetrend.common.time.YoutubeDurationParser;
import com.jeong.youtubetrend.youtube.infrastructure.YoutubeVideoClient;
import com.jeong.youtubetrend.youtube.infrastructure.dto.YoutubeVideoContentDetails;
import com.jeong.youtubetrend.youtube.infrastructure.dto.YoutubeVideoItem;
import com.jeong.youtubetrend.youtube.infrastructure.dto.YoutubeVideoSnippet;
import com.jeong.youtubetrend.youtube.infrastructure.dto.YoutubeVideoStatistics;
import com.jeong.youtubetrend.youtube.infrastructure.dto.YoutubeVideosResponse;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class VideoCollectionServiceTest {

    private final YoutubeVideoClient youtubeVideoClient = Mockito.mock(YoutubeVideoClient.class);
    private final YoutubeVideoMapper youtubeVideoMapper = new YoutubeVideoMapper(new YoutubeDurationParser());
    private final VideoPersistenceService videoPersistenceService = Mockito.mock(VideoPersistenceService.class);

    private final VideoCollectionService videoCollectionService = new VideoCollectionService(
            youtubeVideoClient,
            youtubeVideoMapper,
            videoPersistenceService
    );

    @Test
    @DisplayName("인기 영상 수집 결과를 저장 서비스에 전달한다")
    void collectMostPopularVideos() {
        YoutubeVideosResponse response = new YoutubeVideosResponse(
                List.of(
                        new YoutubeVideoItem(
                                "video-1",
                                new YoutubeVideoSnippet(
                                        "Sample title",
                                        "channel-1",
                                        "2026-04-15T00:00:00Z"
                                ),
                                new YoutubeVideoStatistics(
                                        "1000",
                                        "100",
                                        "10"
                                ),
                                new YoutubeVideoContentDetails("PT5M10S")
                        ),
                        new YoutubeVideoItem(
                                "video-2",
                                new YoutubeVideoSnippet(
                                        "Sample title 2",
                                        "channel-2",
                                        "2026-04-15T00:10:00Z"
                                ),
                                new YoutubeVideoStatistics(
                                        "2000",
                                        "200",
                                        "20"
                                ),
                                new YoutubeVideoContentDetails("PT2M00S")
                        )
                )
        );

        given(youtubeVideoClient.fetchMostPopularVideos("KR", 2)).willReturn(response);

        int savedCount = videoCollectionService.collectMostPopularVideos("KR", 2);

        assertThat(savedCount).isEqualTo(2);
        verify(videoPersistenceService, times(2)).persist(
                any(CollectedVideo.class),
                any(OffsetDateTime.class),
                Mockito.eq("KR"),
                Mockito.isNull(),
                any(Integer.class)
        );
    }
}
