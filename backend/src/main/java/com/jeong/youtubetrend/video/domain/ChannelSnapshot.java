package com.jeong.youtubetrend.video.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.Getter;

@Entity
@Table(name = "channel_snapshot")
@Getter
public class ChannelSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "channel_id", nullable = false)
    private Channel channel;

    @Column(name = "collected_at", nullable = false)
    private OffsetDateTime collectedAt;

    @Column(name = "subscriber_count")
    private Long subscriberCount;

    @Column(name = "hidden_subscriber_count", nullable = false)
    private boolean hiddenSubscriberCount;

    @Column(name = "view_count", nullable = false)
    private Long viewCount;

    @Column(name = "video_count", nullable = false)
    private Long videoCount;

    protected ChannelSnapshot() {

    }

    public ChannelSnapshot(
            Channel channel,
            OffsetDateTime collectedAt,
            Long subscriberCount,
            boolean hiddenSubscriberCount,
            long viewCount,
            long videoCount
    ) {
        this.channel = channel;
        this.collectedAt = collectedAt;
        this.subscriberCount = subscriberCount;
        this.hiddenSubscriberCount = hiddenSubscriberCount;
        this.viewCount = viewCount;
        this.videoCount = videoCount;
    }

}
