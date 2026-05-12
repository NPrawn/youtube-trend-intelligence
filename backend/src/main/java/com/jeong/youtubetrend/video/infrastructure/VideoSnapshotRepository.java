package com.jeong.youtubetrend.video.infrastructure;

import com.jeong.youtubetrend.video.domain.Video;
import com.jeong.youtubetrend.video.domain.VideoSnapshot;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VideoSnapshotRepository extends JpaRepository<VideoSnapshot, Long> {

    long countByVideo(Video video);

    @Query("""
            select max(vs.collectedAt)
            from VideoSnapshot vs
            """)
    Optional<OffsetDateTime> findLatestCollectedAt();

    List<VideoSnapshot> findByCollectedAtAndSourceRegionOrderBySourceRankAsc(
            OffsetDateTime collectedAt,
            String sourceRegion
    );

}
