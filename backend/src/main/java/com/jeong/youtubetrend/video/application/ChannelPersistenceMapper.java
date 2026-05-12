package com.jeong.youtubetrend.video.application;

import com.jeong.youtubetrend.video.domain.Channel;
import com.jeong.youtubetrend.video.domain.ChannelSnapshot;
import java.time.OffsetDateTime;
import org.springframework.stereotype.Component;

@Component
public class ChannelPersistenceMapper {
    public Channel toChannel(CollectedChannel collectedChannel) {
        return new Channel(
                collectedChannel.youtubeChannelId(),
                collectedChannel.title()
        );
    }

    public ChannelSnapshot toChannelSnapshot(
            Channel channel,
            CollectedChannel collectedChannel,
            OffsetDateTime collectedAt
    ) {
        return new ChannelSnapshot(
                channel,
                collectedAt,
                collectedChannel.subscriberCount(),
                collectedChannel.hiddenSubscriberCount(),
                collectedChannel.viewCount(),
                collectedChannel.videoCount()
        );
    }
}
