package com.jeong.youtubetrend.video.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.jeong.youtubetrend.video.domain.Video;
import com.jeong.youtubetrend.video.infrastructure.VideoRepository;
import com.jeong.youtubetrend.video.infrastructure.VideoSnapshotRepository;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class VideoPersistenceServiceTest {

    private final VideoRepository videoRepository = Mockito.mock(VideoRepository.class);
    private final VideoSnapshotRepository videoSnapshotRepository = Mockito.mock(VideoSnapshotRepository.class);
    private final VideoPersistenceMapper videoPersistenceMapper = new VideoPersistenceMapper();

    private final VideoPersistenceService videoPersistenceService = new VideoPersistenceService(
            videoRepository,
            videoSnapshotRepository,
            videoPersistenceMapper
    );

    @Test
    @DisplayName("기존 video가 없으면 새로 저장한 뒤 snapshot을 저장한다")
    void saveVideoAndSnapshotWhenVideoDoesNotExist() {
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

        Video savedVideo = videoPersistenceMapper.toVideo(collectedVideo);

        given(videoRepository.findByYoutubeVideoId("video-1")).willReturn(Optional.empty());
        given(videoRepository.save(any(Video.class))).willReturn(savedVideo);

        videoPersistenceService.persist(
                collectedVideo,
                OffsetDateTime.parse("2026-04-15T01:00:00Z"),
                "KR",
                "10",
                1
        );

        verify(videoRepository, times(1)).save(any(Video.class));
        verify(videoSnapshotRepository, times(1)).save(any());
    }


    @Test
    @DisplayName("기존 video가 있으면 재사용하고 snapshot만 저장한다")
    void reuseVideoAndSaveSnapshotWhenVideoExists() {
        // given
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

        Video existingVideo = videoPersistenceMapper.toVideo(collectedVideo);
        given(videoRepository.findByYoutubeVideoId("video-1")).willReturn(Optional.of(existingVideo));

        // when
        videoPersistenceService.persist(
                collectedVideo,
                OffsetDateTime.parse("2026-04-15T01:00:00Z"),
                "KR",
                "10",
                1
        );

        //then
        verify(videoRepository, times(0)).save(any(Video.class));
        verify(videoSnapshotRepository, times(1)).save(any());
    }
}
