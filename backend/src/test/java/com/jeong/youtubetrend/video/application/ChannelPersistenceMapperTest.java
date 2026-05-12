package com.jeong.youtubetrend.video.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.jeong.youtubetrend.video.domain.Channel;
import com.jeong.youtubetrend.video.domain.ChannelSnapshot;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChannelPersistenceMapperTest {

    private final ChannelPersistenceMapper mapper = new ChannelPersistenceMapper();

    @Test
    @DisplayName("수집 채널을 channel 엔티티로 변환한다")
    void convertsCollectedChannelToChannel() {
        CollectedChannel collectedChannel = new CollectedChannel(
                "channel-1",
                "Sample channel",
                1000L,
                false,
                10000L,
                50L
        );

        Channel channel = mapper.toChannel(collectedChannel);

        assertThat(channel.getYoutubeChannelId()).isEqualTo("channel-1");
        assertThat(channel.getTitle()).isEqualTo("Sample channel");
    }

    @Test
    @DisplayName("수집 채널을 channel snapshot 엔티티로 변환한다")
    void convertsCollectedChannelToChannelSnapshot() {
        CollectedChannel collectedChannel = new CollectedChannel(
                "channel-1",
                "Sample channel",
                1000L,
                false,
                10000L,
                50L
        );

        Channel channel = mapper.toChannel(collectedChannel);
        OffsetDateTime collectedAt = OffsetDateTime.parse("2026-05-12T10:00:00Z");

        ChannelSnapshot snapshot = mapper.toChannelSnapshot(
                channel,
                collectedChannel,
                collectedAt
        );

        assertThat(snapshot.getChannel()).isEqualTo(channel);
        assertThat(snapshot.getCollectedAt()).isEqualTo(collectedAt);
        assertThat(snapshot.getSubscriberCount()).isEqualTo(1000L);
        assertThat(snapshot.isHiddenSubscriberCount()).isFalse();
        assertThat(snapshot.getViewCount()).isEqualTo(10000L);
        assertThat(snapshot.getVideoCount()).isEqualTo(50L);
    }

}
