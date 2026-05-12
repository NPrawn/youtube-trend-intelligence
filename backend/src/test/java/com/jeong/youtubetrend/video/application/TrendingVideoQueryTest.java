package com.jeong.youtubetrend.video.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.jeong.youtubetrend.video.api.response.TrendingVideosResponse;
import com.jeong.youtubetrend.video.domain.Video;
import com.jeong.youtubetrend.video.domain.VideoSnapshot;
import com.jeong.youtubetrend.video.infrastructure.VideoSnapshotRepository;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TrendingVideoQueryTest {
    private final VideoSnapshotRepository videoSnapshotRepository = Mockito.mock(VideoSnapshotRepository.class);
    private final TrendingVideoQueryService trendingVideoQueryService =
            new TrendingVideoQueryService(videoSnapshotRepository);

    @Test
    @DisplayName("최신 수집 시점의 인기 영상을 조회한다")
    void findTrendingVideos() {
        OffsetDateTime latestCollectedAt = OffsetDateTime.parse("2026-04-15T01:00:00Z");

        Video shortVideo = new Video(
                "video-1",
                "Short Video",
                "channel-1",
                OffsetDateTime.parse("2026-04-15T00:00:00Z"),
                120,
                true
        );

        Video longVideo = new Video(
                "video-2",
                "Long Video",
                "channel-2",
                OffsetDateTime.parse("2026-04-15T00:10:00Z"),
                600,
                false
        );

        VideoSnapshot shortSnapshot = new VideoSnapshot(
                shortVideo,
                latestCollectedAt,
                1000L,
                100L,
                10L,
                "KR",
                "10",
                1
        );

        VideoSnapshot longSnapshot = new VideoSnapshot(
                longVideo,
                latestCollectedAt,
                2000L,
                200L,
                20L,
                "KR",
                "10",
                2
        );

        given(videoSnapshotRepository.findLatestCollectedAt()).willReturn(Optional.of(latestCollectedAt));
        given(videoSnapshotRepository.findByCollectedAtAndSourceRegionOrderBySourceRankAsc(latestCollectedAt, "KR"))
                .willReturn(List.of(shortSnapshot, longSnapshot));

        TrendingVideosResponse response = trendingVideoQueryService.findTrendingVideos("KR", VideoFormType.ALL, 20);

        assertThat(response.items()).hasSize(2);
        assertThat(response.items().get(0).videoId()).isEqualTo("video-1");
        assertThat(response.items().get(0).shortForm()).isTrue();
        assertThat(response.items().get(1).videoId()).isEqualTo("video-2");
        assertThat(response.items().get(1).shortForm()).isFalse();
    }

    @Test
    @DisplayName("short form만 필터링할 수 있다")
    void filterShortForm() {
        OffsetDateTime latestCollectedAt = OffsetDateTime.parse("2026-04-15T01:00:00Z");

        Video shortVideo = new Video(
                "video-1",
                "Short video",
                "channel-1",
                OffsetDateTime.parse("2026-04-15T00:00:00Z"),
                120,
                true
        );

        Video longVideo = new Video(
                "video-2",
                "Long video",
                "channel-2",
                OffsetDateTime.parse("2026-04-15T00:10:00Z"),
                600,
                false
        );

        VideoSnapshot shortSnapShot = new VideoSnapshot(
                shortVideo,
                latestCollectedAt,
                1000L,
                100L,
                10L,
                "KR",
                "10",
                1
        );

        VideoSnapshot longSnapshot = new VideoSnapshot(
                longVideo,
                latestCollectedAt,
                2000L,
                200L,
                20L,
                "KR",
                "10",
                2
        );

        given(videoSnapshotRepository.findLatestCollectedAt()).willReturn(Optional.of(latestCollectedAt));
        given(videoSnapshotRepository.findByCollectedAtAndSourceRegionOrderBySourceRankAsc(latestCollectedAt, "KR"))
                .willReturn(List.of(shortSnapShot, longSnapshot));

        TrendingVideosResponse response = trendingVideoQueryService.findTrendingVideos("KR", VideoFormType.SHORT, 20);

        assertThat(response.items()).hasSize(1);
        assertThat(response.items().get(0).videoId()).isEqualTo("video-1");
        assertThat(response.items().get(0).shortForm()).isTrue();
    }

    @Test
    @DisplayName("최신 수집 데이터가 없으면 빈 목록을 반환한다")
    void returnEmptyItemsWhenSnapshotExists() {
        given(videoSnapshotRepository.findLatestCollectedAt()).willReturn(Optional.empty());

        TrendingVideosResponse response = trendingVideoQueryService.findTrendingVideos("KR", VideoFormType.ALL, 20);

        assertThat(response.items()).isEmpty();
    }
}
