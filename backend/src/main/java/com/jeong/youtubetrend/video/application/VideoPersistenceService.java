package com.jeong.youtubetrend.video.application;

import com.jeong.youtubetrend.video.domain.Video;
import com.jeong.youtubetrend.video.domain.VideoSnapshot;
import com.jeong.youtubetrend.video.infrastructure.VideoRepository;
import com.jeong.youtubetrend.video.infrastructure.VideoSnapshotRepository;
import java.time.OffsetDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VideoPersistenceService {

    private final VideoRepository videoRepository;
    private final VideoSnapshotRepository videoSnapshotRepository;
    private final VideoPersistenceMapper videoPersistenceMapper;

    public VideoPersistenceService(
            VideoRepository videoRepository,
            VideoSnapshotRepository videoSnapshotRepository,
            VideoPersistenceMapper videoPersistenceMapper
    ) {
        this.videoRepository = videoRepository;
        this.videoSnapshotRepository = videoSnapshotRepository;
        this.videoPersistenceMapper = videoPersistenceMapper;
    }

    @Transactional
    public void persist(
            CollectedVideo collectedVideo,
            OffsetDateTime collectedAt,
            String sourceRegion,
            String sourceCategory,
            Integer sourceRank
    ) {
        Video video = findOrCreateVideo(collectedVideo);

        VideoSnapshot videoSnapshot = videoPersistenceMapper.toVideoSnapshot(
                video,
                collectedVideo,
                collectedAt,
                sourceRegion,
                sourceCategory,
                sourceRank
        );

        videoSnapshotRepository.save(videoSnapshot);
    }

    private Video findOrCreateVideo(CollectedVideo collectedVideo) {
        return videoRepository.findByYoutubeVideoId(collectedVideo.youtubeVideoId())
                .orElseGet(() -> videoRepository.save(videoPersistenceMapper.toVideo(collectedVideo)));
    }

}
