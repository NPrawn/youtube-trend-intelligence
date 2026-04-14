package com.jeong.youtubetrend.collector.video;

import com.jeong.youtubetrend.common.time.YoutubeDurationParser;
import com.jeong.youtubetrend.infra.youtube.dto.YoutubeVideoItem;
import java.time.OffsetDateTime;
import org.springframework.stereotype.Component;

@Component
public class YoutubeVideoMapper {
    private static final int SHORT_FROM_MAX_SECONDS = 180;

    private final YoutubeDurationParser durationParser;

    public YoutubeVideoMapper(YoutubeDurationParser durationParser) {
        this.durationParser = durationParser;
    }

    public CollectedVideo map(YoutubeVideoItem item) {
        int durationSeconds = durationParser.parseToSecond(item.contentDetails().duration());

        return new CollectedVideo(
                item.id(),
                item.snippet().title(),
                item.snippet().channelId(),
                OffsetDateTime.parse(item.snippet().publishedAt()),
                durationSeconds,
                durationSeconds <= SHORT_FROM_MAX_SECONDS,
                Long.parseLong(item.statistics().viewCount()),
                parseNullableLong(item.statistics().likeCount()),
                parseNullableLong(item.statistics().commentCount())
        );
    }

    private Long parseNullableLong(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return Long.parseLong(value);
    }
}
