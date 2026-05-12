package com.jeong.youtubetrend.video.infrastructure;

import com.jeong.youtubetrend.video.domain.Channel;
import com.jeong.youtubetrend.video.domain.ChannelSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelSnapshotRepository extends JpaRepository<ChannelSnapshot, Long> {

    long countByChannel(Channel channel);

}
