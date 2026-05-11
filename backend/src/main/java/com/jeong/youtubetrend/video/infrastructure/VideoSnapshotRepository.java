package com.jeong.youtubetrend.video.infrastructure;

import com.jeong.youtubetrend.video.domain.Video;
import com.jeong.youtubetrend.video.domain.VideoSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoSnapshotRepository extends JpaRepository<VideoSnapshot, Long> {

    long countByVideo(Video video);

}
