package com.jeong.youtubetrend.collector.video;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.jeong.youtubetrend.common.time.YoutubeDurationParser;
import com.jeong.youtubetrend.infra.youtube.dto.YoutubeVideoContentDetails;
import com.jeong.youtubetrend.infra.youtube.dto.YoutubeVideoItem;
import com.jeong.youtubetrend.infra.youtube.dto.YoutubeVideoSnippet;
import com.jeong.youtubetrend.infra.youtube.dto.YoutubeVideoStatistics;
import org.junit.jupiter.api.Test;

public class YoutubeVideoMapperTest {

    private final YoutubeVideoMapper mapper = new YoutubeVideoMapper(new YoutubeDurationParser());

    @Test
    void mapVideoItemToCollectedVideo() {
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

        CollectedVideo collectedVideo = mapper.map(item);

        assertEquals("video-1", collectedVideo.youtubeVideoId());
        assertEquals("Sample title", collectedVideo.title( ));
        assertEquals("channel-1", collectedVideo.youtubeChannelId());
        assertEquals(310, collectedVideo.durationSeconds());
        assertFalse(collectedVideo.shortForm());
        assertEquals(1000L, collectedVideo.viewCount());
        assertEquals(100L, collectedVideo.likeCount());
        assertEquals(10L, collectedVideo.commentCount());
    }

}
