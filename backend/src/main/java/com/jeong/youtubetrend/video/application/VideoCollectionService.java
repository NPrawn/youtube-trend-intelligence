package com.jeong.youtubetrend.video.application;

import com.jeong.youtubetrend.youtube.infrastructure.YoutubeVideoClient;
import com.jeong.youtubetrend.youtube.infrastructure.dto.YoutubeVideoItem;
import com.jeong.youtubetrend.youtube.infrastructure.dto.YoutubeVideosResponse;
import java.time.OffsetDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VideoCollectionService {

    private final YoutubeVideoClient youtubeVideoClient;
    private final YoutubeVideoMapper youtubeVideoMapper;
    private final VideoPersistenceService videoPersistenceService;

    public VideoCollectionService(
            YoutubeVideoClient youtubeVideoClient,
            YoutubeVideoMapper youtubeVideoMapper,
            VideoPersistenceService videoPersistenceService
    ) {
        this.youtubeVideoClient = youtubeVideoClient;
        this.youtubeVideoMapper = youtubeVideoMapper;
        this.videoPersistenceService = videoPersistenceService;
    }

    @Transactional
    public int collectMostPopularVideos(String regionCode, int maxResults) {
        YoutubeVideosResponse response = youtubeVideoClient.fetchMostPopularVideos(regionCode, maxResults);
        OffsetDateTime collectedAt = OffsetDateTime.now();

        int savedCount = 0;
        int rank = 1;

        for (YoutubeVideoItem item : response.items()) {
            CollectedVideo collectedVideo = youtubeVideoMapper.map(item);

            videoPersistenceService.persist(
                    collectedVideo,
                    collectedAt,
                    regionCode,
                    null,
                    rank
            );

            savedCount++;
            rank++;
        }

        return savedCount;
    }

}
