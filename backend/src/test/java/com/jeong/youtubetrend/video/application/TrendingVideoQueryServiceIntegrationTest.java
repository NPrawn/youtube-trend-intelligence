package com.jeong.youtubetrend.video.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.jeong.youtubetrend.video.api.response.TrendingVideosResponse;
import com.jeong.youtubetrend.video.domain.Video;
import com.jeong.youtubetrend.video.domain.VideoSnapshot;
import com.jeong.youtubetrend.video.infrastructure.VideoRepository;
import com.jeong.youtubetrend.video.infrastructure.VideoSnapshotRepository;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Sql(
        statements = {
                "delete from video_snapshot",
                "delete from video"
        },
        executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
)
public class TrendingVideoQueryServiceIntegrationTest {

    @Autowired
    private TrendingVideoQueryService trendingVideoQueryService;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoSnapshotRepository videoSnapshotRepository;

    @Test
    @DisplayName("가장 최근 collectedAt 기준으로 region, form, limit을 적용해 조회한다")
    void findTrendingVideosWithLatestCollectedAtFilters() {
        Video oldVideo = videoRepository.save(new Video(
                "video-old",
                "Old video",
                "channel-old",
                OffsetDateTime.parse("2026-04-14T00:00:00Z"),
                400,
                false
        ));

        Video shortVideo = videoRepository.save(new Video(
                "video-short",
                "Short video",
                "channel-short",
                OffsetDateTime.parse("2026-04-15T00:00:00Z"),
                120,
                true
        ));

        Video longVideo = videoRepository.save(new Video(
                "video-long",
                "Long video",
                "channel-long",
                OffsetDateTime.parse("2026-04-15T00:10:00Z"),
                600,
                false
        ));

        videoSnapshotRepository.save(new VideoSnapshot(
                oldVideo,
                OffsetDateTime.parse("2026-04-15T00:00:00Z"),
                500L,
                50L,
                5L,
                "KR",
                "10",
                1
        ));

        videoSnapshotRepository.save(new VideoSnapshot(
                shortVideo,
                OffsetDateTime.parse("2026-04-15T01:00:00Z"),
                1000L,
                100L,
                10L,
                "KR",
                "10",
                2
        ));

        videoSnapshotRepository.save(new VideoSnapshot(
                longVideo,
                OffsetDateTime.parse("2026-04-15T01:00:00Z"),
                2000L,
                200L,
                20L,
                "KR",
                "10",
                1
        ));

        videoSnapshotRepository.save(new VideoSnapshot(
                longVideo,
                OffsetDateTime.parse("2026-04-15T01:00:00Z"),
                3000L,
                300L,
                30L,
                "US",
                "10",
                1
        ));

        TrendingVideosResponse allResponse =
                trendingVideoQueryService.findTrendingVideos("KR", VideoFormType.ALL, 20);

        assertThat(allResponse.items()).hasSize(2);
        assertThat(allResponse.items().get(0).videoId()).isEqualTo("video-long");
        assertThat(allResponse.items().get(1).videoId()).isEqualTo("video-short");

        TrendingVideosResponse shortResponse =
                trendingVideoQueryService.findTrendingVideos("KR", VideoFormType.SHORT, 20);

        assertThat(shortResponse.items()).hasSize(1);
        assertThat(shortResponse.items().get(0).videoId()).isEqualTo("video-short");

        TrendingVideosResponse limitedResponse =
                trendingVideoQueryService.findTrendingVideos("KR", VideoFormType.ALL, 1);

        assertThat(limitedResponse.items()).hasSize(1);
        assertThat(limitedResponse.items().get(0).videoId()).isEqualTo("video-long");
    }

}
