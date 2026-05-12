package com.jeong.youtubetrend.video.application;

import com.jeong.youtubetrend.video.api.response.TrendingVideoItemResponse;
import com.jeong.youtubetrend.video.api.response.TrendingVideosResponse;
import com.jeong.youtubetrend.video.domain.VideoSnapshot;
import com.jeong.youtubetrend.video.infrastructure.VideoSnapshotRepository;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TrendingVideoQueryService {

    private final VideoSnapshotRepository videoSnapshotRepository;

    public TrendingVideoQueryService(VideoSnapshotRepository videoSnapshotRepository) {
        this.videoSnapshotRepository = videoSnapshotRepository;
    }

    public TrendingVideosResponse findTrendingVideos(String region, VideoFormType form, int limit) {
        return videoSnapshotRepository.findLatestCollectedAt()
                .map(latestCollectedAt -> findTrendingVideos(region, form, limit, latestCollectedAt))
                .orElseGet(() -> new TrendingVideosResponse(List.of()));
    }

    private TrendingVideosResponse findTrendingVideos(
            String region,
            VideoFormType form,
            int limit,
            OffsetDateTime latestCollectedAt
    ) {
        List<TrendingVideoItemResponse> items = videoSnapshotRepository
                .findByCollectedAtAndSourceRegionOrderBySourceRankAsc(latestCollectedAt, region)
                .stream()
                .filter(snapshot -> matchesForm(snapshot, form))
                .limit(limit)
                .map(this::toResponse)
                .toList();

        return new TrendingVideosResponse(items);
    }

    private boolean matchesForm(VideoSnapshot snapshot, VideoFormType form) {
        return switch (form) {
            case ALL -> true;
            case SHORT -> snapshot.getVideo().isShortForm();
            case LONG -> !snapshot.getVideo().isShortForm();
        };
    }

    private TrendingVideoItemResponse toResponse(VideoSnapshot snapshot) {
        return new TrendingVideoItemResponse(
                snapshot.getVideo().getYoutubeVideoId(),
                snapshot.getVideo().getTitle(),
                snapshot.getVideo().getYoutubeChannelId(),
                snapshot.getVideo().getPublishedAt(),
                snapshot.getVideo().getDurationSeconds(),
                snapshot.getVideo().isShortForm(),
                snapshot.getViewCount(),
                snapshot.getLikeCount(),
                snapshot.getCommentCount(),
                snapshot.getSourceRegion(),
                snapshot.getSourceCategory(),
                snapshot.getSourceRank(),
                snapshot.getCollectedAt()
        );
    }
}
