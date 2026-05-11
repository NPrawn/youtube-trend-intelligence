package com.jeong.youtubetrend.video.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.jeong.youtubetrend.video.domain.Video;
import com.jeong.youtubetrend.video.domain.VideoSnapshot;
import com.jeong.youtubetrend.video.infrastructure.VideoRepository;
import com.jeong.youtubetrend.video.infrastructure.VideoSnapshotRepository;
import java.time.OffsetDateTime;
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
class VideoPersistenceServiceIntegrationTest {

    @Autowired
    private VideoPersistenceService videoPersistenceService;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoSnapshotRepository videoSnapshotRepository;

    @Test
    @DisplayName("처음 수집하면 video와 snapshot이 저장된다")
    void saveVideoAndSnapshotOnFirstCollection() {
        // given
        CollectedVideo collectedVideo = new CollectedVideo(
                "integration-video-1",
                "Sample title",
                "channel-1",
                OffsetDateTime.parse("2026-04-15T00:00:00Z"),
                120,
                true,
                1000L,
                100L,
                10L
        );

        // when
        videoPersistenceService.persist(
                collectedVideo,
                OffsetDateTime.parse("2026-04-15T01:00:00Z"),
                "KR",
                "10",
                1
        );

        // then
        Video video = videoRepository.findByYoutubeVideoId("integration-video-1").orElseThrow();

        assertThat(video.getYoutubeVideoId()).isEqualTo("integration-video-1");
        assertThat(video.getTitle()).isEqualTo("Sample title");
        assertThat(videoSnapshotRepository.countByVideo(video)).isEqualTo(1);

        VideoSnapshot snapshot = videoSnapshotRepository.findAll().get(0);

        assertThat(snapshot.getCollectedAt()).isEqualTo(OffsetDateTime.parse("2026-04-15T01:00:00Z"));
        assertThat(snapshot.getViewCount()).isEqualTo(1000L);
        assertThat(snapshot.getLikeCount()).isEqualTo(100L);
        assertThat(snapshot.getCommentCount()).isEqualTo(10L);
        assertThat(snapshot.getSourceRegion()).isEqualTo("KR");
        assertThat(snapshot.getSourceCategory()).isEqualTo("10");
        assertThat(snapshot.getSourceRank()).isEqualTo(1);
    }

    @Test
    @DisplayName("같은 youtube video id를 다시 수집하면 video는 재사용하고 snapshot만 추가 저장한다")
    void reuseVideoAndAppendSnapshotOnRecollection() {
        // given
        CollectedVideo firstCollectedVideo = new CollectedVideo(
                "integration-video-2",
                "Sample title",
                "channel-1",
                OffsetDateTime.parse("2026-04-15T00:00:00Z"),
                120,
                true,
                1000L,
                100L,
                10L
        );

        CollectedVideo secondCollectedVideo = new CollectedVideo(
                "integration-video-2",
                "Sample title",
                "channel-1",
                OffsetDateTime.parse("2026-04-15T00:00:00Z"),
                120,
                true,
                2000L,
                150L,
                20L
        );

        // when
        videoPersistenceService.persist(
                firstCollectedVideo,
                OffsetDateTime.parse("2026-04-15T01:00:00Z"),
                "KR",
                "10",
                1
        );

        videoPersistenceService.persist(
                secondCollectedVideo,
                OffsetDateTime.parse("2026-04-15T02:00:00Z"),
                "KR",
                "10",
                1
        );

        // then
        Video video = videoRepository.findByYoutubeVideoId("integration-video-2").orElseThrow();

        assertThat(video.getYoutubeVideoId()).isEqualTo("integration-video-2");
        assertThat(videoSnapshotRepository.countByVideo(video)).isEqualTo(2);
    }
}
