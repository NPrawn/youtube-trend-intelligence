package com.jeong.youtubetrend.video.application;

import com.jeong.youtubetrend.video.domain.Video;
import com.jeong.youtubetrend.video.domain.VideoSnapshot;
import java.time.OffsetDateTime;
import org.springframework.stereotype.Component;

@Component
public class VideoPersistenceMapper {

    public Video toVideo(CollectedVideo collectedVideo) {
        return new Video(
                collectedVideo.youtubeVideoId(),
                collectedVideo.title(),
                collectedVideo.youtubeChannelId(),
                collectedVideo.publishedAt(),
                collectedVideo.durationSeconds(),
                collectedVideo.shortForm()
        );
    }

    public VideoSnapshot toVideoSnapshot(
            Video video,
            CollectedVideo collectedVideo,
            OffsetDateTime collectedAt,
            String sourceRegion,
            String sourceCategory,
            Integer sourceRank
    ) {
        return new VideoSnapshot(
                video,
                collectedAt,
                collectedVideo.viewCount(),
                collectedVideo.likeCount(),
                collectedVideo.commentCount(),
                sourceRegion,
                sourceCategory,
                sourceRank
        );
    }

}
