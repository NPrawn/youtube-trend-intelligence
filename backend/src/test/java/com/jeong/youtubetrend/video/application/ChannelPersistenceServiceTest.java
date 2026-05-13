package com.jeong.youtubetrend.video.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.jeong.youtubetrend.video.domain.Channel;
import com.jeong.youtubetrend.video.domain.ChannelSnapshot;
import com.jeong.youtubetrend.video.infrastructure.ChannelRepository;
import com.jeong.youtubetrend.video.infrastructure.ChannelSnapshotRepository;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ChannelPersistenceServiceTest {
    private final ChannelRepository channelRepository = Mockito.mock(ChannelRepository.class);
    private final ChannelSnapshotRepository channelSnapshotRepository = Mockito.mock(ChannelSnapshotRepository.class);
    private final ChannelPersistenceMapper channelPersistenceMapper = new ChannelPersistenceMapper();

    private final ChannelPersistenceService channelPersistenceService = new ChannelPersistenceService(
            channelRepository, channelSnapshotRepository, channelPersistenceMapper
    );

    @Test
    @DisplayName("기존 channel이 없으면 새로 저장한 뒤 snapshot을 저장한다.")
    void saveChannelAndSnapshotWhenChannelDoesNotExists(){
        CollectedChannel collectedChannel = new CollectedChannel(
                "channel-1",
                "Sample channel",
                1000L,
                false,
                10000L,
                50L
        );

        Channel existingChannel = channelPersistenceMapper.toChannel(collectedChannel);
        given(channelRepository.findByYoutubeChannelId("channel-1")).willReturn(Optional.of(existingChannel));

        channelPersistenceService.persist(
                collectedChannel,
                OffsetDateTime.parse("2026-05-13T10:00:00Z")
        );

        verify(channelRepository, never()).saveAndFlush(any(Channel.class));
        verify(channelSnapshotRepository, times(1)).save(any());
    }
}
