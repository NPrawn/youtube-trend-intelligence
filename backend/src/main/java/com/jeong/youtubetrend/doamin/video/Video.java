package com.jeong.youtubetrend.doamin.video;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;

@Entity
@Table(name = "video")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "youtube_video_id", nullable = false, unique = true, length = 32)
    private String youtubeVideoId;

    @Column(nullable = false, length = 500)
    private String title;

    @Column(name = "youtube_channel_id", nullable = false, length = 64)
    private String youtubeChannelId;

    @Column(name = "published_at", nullable = false)
    private OffsetDateTime publishedAt;

    @Column(name = "duration_seconds", nullable = false)
    private int durationSeconds;

    @Column(name = "is_short_form", nullable = false)
    private boolean shortForm;

    protected Video() {

    }

    public Video(
            String youtubeVideoId,
            String title,
            String youtubeChannelId,
            OffsetDateTime publishedAt,
            int durationSeconds,
            boolean shortForm
    ) {
        this.youtubeVideoId = youtubeVideoId;
        this.title = title;
        this.youtubeChannelId = youtubeChannelId;
        this.publishedAt = publishedAt;
        this.durationSeconds = durationSeconds;
        this.shortForm = shortForm;
    }

    public Long getId() {
        return id;
    }

    public String getYoutubeVideoId() {
        return youtubeVideoId;
    }

    public String getTitle() {
        return title;
    }

    public String getYoutubeChannelId() {
        return youtubeChannelId;
    }

    public OffsetDateTime getPublishedAt() {
        return publishedAt;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public boolean isShortForm() {
        return shortForm;
    }
}
