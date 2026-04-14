package com.jeong.youtubetrend.video.infrastructure;

import com.jeong.youtubetrend.video.domain.Video;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {

    Optional<Video> findByYoutubeVideoId(String youtubeVideoId);
}
