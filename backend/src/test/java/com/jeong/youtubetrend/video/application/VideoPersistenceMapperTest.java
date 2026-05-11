package com.jeong.youtubetrend.video.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.jeong.youtubetrend.video.domain.Video;
import com.jeong.youtubetrend.video.domain.VideoSnapshot;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class VideoPersistenceMapperTest {

    private final VideoPersistenceMapper mapper = new VideoPersistenceMapper();

    @Test
    @DisplayName("수집 비디오를 video 엔티티로 변환한다")
    void convertsCollectedVideoToVideo() {

        OffsetDateTime publishedAt = OffsetDateTime.parse("2026-04-15T00:00:00Z");
        CollectedVideo collectedVideo = new CollectedVideo(
                "video-1",
                "Sample title",
                "channel-1",
                publishedAt,
                120,
                true,
                1000L,
                100L,
                10L
        );

        Video video = mapper.toVideo(collectedVideo);

        assertThat(video.getYoutubeVideoId()).isEqualTo("video-1");
        assertThat(video.getTitle()).isEqualTo("Sample title");
        assertThat(video.getYoutubeChannelId()).isEqualTo("channel-1");
        assertThat(video.getPublishedAt()).isEqualTo(publishedAt);
        assertThat(video.getDurationSeconds()).isEqualTo(120);
        assertThat(video.isShortForm()).isTrue();
    }

    @Test
    @DisplayName("수집 비디오를 video snapshot 엔티티로 변환한다")
    void convertsCollectedVideoToSnapshot() {
        CollectedVideo collectedVideo = new CollectedVideo(
                "video-1",
                "Sample title",
                "channel-1",
                OffsetDateTime.parse("2026-04-15T00:00:00Z"),
                120,
                true,
                1000L,
                100L,
                10L
        );

        Video video = mapper.toVideo(collectedVideo);
        OffsetDateTime collectedAt = OffsetDateTime.parse("2026-04-15T01:00:00Z");

        VideoSnapshot snapshot = mapper.toVideoSnapshot(
                video,
                collectedVideo,
                collectedAt,
                "KR",
                "10",
                1
        );

        assertThat(snapshot.getVideo()).isEqualTo(video);
        assertThat(snapshot.getCollectedAt()).isEqualTo(collectedAt);
        assertThat(snapshot.getSourceRegion()).isEqualTo("KR");
        assertThat(snapshot.getViewCount()).isEqualTo(1000L);
        assertThat(snapshot.getLikeCount()).isEqualTo(100L);
        assertThat(snapshot.getCommentCount()).isEqualTo(10L);
        assertThat(snapshot.getSourceCategory()).isEqualTo("10");
        assertThat(snapshot.getSourceRank()).isEqualTo(1);

    }
}
