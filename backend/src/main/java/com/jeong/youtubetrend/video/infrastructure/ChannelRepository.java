package com.jeong.youtubetrend.video.infrastructure;

import com.jeong.youtubetrend.video.domain.Channel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<Channel, Long> {

    Optional<Channel> findByYoutubeChannelId(String youtubeChannelId);

}
