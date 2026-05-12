package com.jeong.youtubetrend.video.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "channel")
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "youtube_channel_id", nullable = false, unique = true)
    private String youtubeChannelId;

    @Column(nullable = false, length = 255)
    private String title;

    protected Channel() {

    }

    public Channel(String youtubeChannelId, String title) {
        this.youtubeChannelId = youtubeChannelId;
        this.title = title;
    }

}
