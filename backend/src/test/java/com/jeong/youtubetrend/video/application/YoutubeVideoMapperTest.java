package com.jeong.youtubetrend.video.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.jeong.youtubetrend.common.time.YoutubeDurationParser;
import com.jeong.youtubetrend.youtube.infrastructure.dto.YoutubeVideoContentDetails;
import com.jeong.youtubetrend.youtube.infrastructure.dto.YoutubeVideoItem;
import com.jeong.youtubetrend.youtube.infrastructure.dto.YoutubeVideoSnippet;
import com.jeong.youtubetrend.youtube.infrastructure.dto.YoutubeVideoStatistics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class YoutubeVideoMapperTest {

    private final YoutubeVideoMapper mapper = new YoutubeVideoMapper(new YoutubeDurationParser());

    @Test
    @DisplayName("유튜브 응답을 수집용 비디오 모델로 변환한다")
    void mapsYoutubeVideoItemToCollectedVideo() {
        // given
        YoutubeVideoItem item = new YoutubeVideoItem(
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
        );

        // when
        CollectedVideo collectedVideo = mapper.map(item);

        // then
        assertThat(collectedVideo.youtubeVideoId()).isEqualTo("video-1");
        assertThat(collectedVideo.title()).isEqualTo("Sample title");
        assertThat(collectedVideo.youtubeChannelId()).isEqualTo("channel-1");
        assertThat(collectedVideo.durationSeconds()).isEqualTo(310);
        assertThat(collectedVideo.shortForm()).isFalse();
        assertThat(collectedVideo.viewCount()).isEqualTo(1000L);
        assertThat(collectedVideo.likeCount()).isEqualTo(100L);
        assertThat(collectedVideo.commentCount()).isEqualTo(10L);
    }

    @Test
    @DisplayName("좋아요 수와 댓글 수가 없으면 null로 변환한다")
    void mapsMissingReactionCountsToNull() {
        // given
        YoutubeVideoItem item = new YoutubeVideoItem(
                "video-1",
                new YoutubeVideoSnippet(
                        "Sample title",
                        "channel-1",
                        "2026-04-14T00:00:00Z"
                ),
                new YoutubeVideoStatistics(
                        "1000",
                        null,
                        null
                ),
                new YoutubeVideoContentDetails("PT59S")
        );

        // when
        CollectedVideo collectedVideo = mapper.map(item);

        // then
        assertThat(collectedVideo.shortForm()).isTrue();
        assertThat(collectedVideo.likeCount()).isNull();
        assertThat(collectedVideo.commentCount()).isNull();
    }
}
