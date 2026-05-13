package com.jeong.youtubetrend.video.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.jeong.youtubetrend.video.domain.Channel;
import com.jeong.youtubetrend.video.domain.ChannelSnapshot;
import com.jeong.youtubetrend.video.infrastructure.ChannelRepository;
import com.jeong.youtubetrend.video.infrastructure.ChannelSnapshotRepository;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Sql(
        statements = {
                "delete from channel_snapshot",
                "delete from channel"
        },
        executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
)
public class ChannelPersistenceServiceIntegrationTest {

    @Autowired
    private ChannelPersistenceService channelPersistenceService;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private ChannelSnapshotRepository channelSnapshotRepository;

    @Test
    @DisplayName("처음 수집하면 channel과 snapshot이 저장된다")
    void saveChannelAndSnapshotOnFirstCollection() {
        CollectedChannel collectedChannel = new CollectedChannel(
                "integration-channel-1",
                "Sample channel",
                1000L,
                false,
                10000L,
                50L
        );

        channelPersistenceService.persist(
                collectedChannel,
                OffsetDateTime.parse("2026-05-13T10:00:00Z")
        );

        Channel channel = channelRepository.findByYoutubeChannelId("integration-channel-1").orElseThrow();
        assertThat(channel.getYoutubeChannelId()).isEqualTo("integration-channel-1");
        assertThat(channel.getTitle()).isEqualTo("Sample channel");
        assertThat(channelSnapshotRepository.countByChannel(channel)).isEqualTo(1);

        ChannelSnapshot snapshot = channelSnapshotRepository.findAll().get(0);

        assertThat(snapshot.getSubscriberCount()).isEqualTo(1000L);
        assertThat(snapshot.isHiddenSubscriberCount()).isFalse();
        assertThat(snapshot.getViewCount()).isEqualTo(10000L);
        assertThat(snapshot.getVideoCount()).isEqualTo(50L);
        assertThat(snapshot.getCollectedAt()).isEqualTo(OffsetDateTime.parse("2026-05-13T10:00:00Z"));
    }

    @Test
    @DisplayName("같은 youtube channel id를 다시 수집하면 channel은 재사용하고 snapshot만 추가 저장한다")
    void reuseChannelAndAppendSnapshotOnRecollection() {
        CollectedChannel firstCollectedChannel = new CollectedChannel(
                "integration-channel-2",
                "Sample channel",
                1000L,
                false,
                10000L,
                50L
        );
        CollectedChannel secondCollectedChannel = new CollectedChannel(
                "integration-channel-2",
                "Sample channel",
                1200L,
                false,
                12000L,
                55L
        );

        channelPersistenceService.persist(
                firstCollectedChannel,
                OffsetDateTime.parse("2026-05-13T10:00:00Z")
        );

        channelPersistenceService.persist(
                secondCollectedChannel,
                OffsetDateTime.parse("2026-05-13T11:00:00Z")
        );

        Channel channel = channelRepository.findByYoutubeChannelId("integration-channel-2").orElseThrow();

        assertThat(channel.getYoutubeChannelId()).isEqualTo("integration-channel-2");
        assertThat(channelSnapshotRepository.countByChannel(channel)).isEqualTo(2);
        assertThat(channelRepository.count()).isEqualTo(1);
    }

}
