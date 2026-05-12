package com.jeong.youtubetrend.video.application;

import com.jeong.youtubetrend.video.domain.Channel;
import com.jeong.youtubetrend.video.domain.ChannelSnapshot;
import com.jeong.youtubetrend.video.infrastructure.ChannelRepository;
import com.jeong.youtubetrend.video.infrastructure.ChannelSnapshotRepository;
import java.time.OffsetDateTime;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChannelPersistenceService {

    private final ChannelRepository channelRepository;
    private final ChannelSnapshotRepository channelSnapshotRepository;
    private final ChannelPersistenceMapper channelPersistenceMapper;

    public ChannelPersistenceService(
            ChannelRepository channelRepository,
            ChannelSnapshotRepository channelSnapshotRepository,
            ChannelPersistenceMapper channelPersistenceMapper
    ) {
       this.channelRepository = channelRepository;
       this.channelSnapshotRepository = channelSnapshotRepository;
       this.channelPersistenceMapper = channelPersistenceMapper;
    }

    @Transactional
    public void persist(CollectedChannel collectedChannel, OffsetDateTime collectedAt) {
        Channel channel = findOrCreateChannel(collectedChannel);

        ChannelSnapshot channelSnapshot = channelPersistenceMapper.toChannelSnapshot(
                channel,
                collectedChannel,
                collectedAt
        );

        channelSnapshotRepository.save(channelSnapshot);
    }

    private Channel findOrCreateChannel(CollectedChannel collectedChannel) {
        return channelRepository.findByYoutubeChannelId(collectedChannel.youtubeChannelId())
                .orElseGet(() -> saveOrFindExistingChannel(collectedChannel));
    }

    private Channel saveOrFindExistingChannel(CollectedChannel collectedChannel) {
        try {
            return channelRepository.saveAndFlush(channelPersistenceMapper.toChannel(collectedChannel));
        } catch (DataIntegrityViolationException exception) {
            return channelRepository.findByYoutubeChannelId(collectedChannel.youtubeChannelId())
                    .orElseThrow(() -> exception);
        }
    }
}
