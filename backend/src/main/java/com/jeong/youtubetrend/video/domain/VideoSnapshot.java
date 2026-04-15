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

@Entity
@Table(name = "video_snapshot")
public class VideoSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    @Column(name = "collected_at", nullable = false)
    private OffsetDateTime collectedAt;

    @Column(name = "view_count", nullable = false)
    private long viewCount;

    @Column(name = "like_count")
    private Long likeCount;

    @Column(name = "comment_count")
    private Long commentCount;

    @Column(name = "source_region", nullable = false, length = 8)
    private String sourceRegion;

    @Column(name = "source_category", length = 32)
    private String sourceCategory;

    @Column(name = "source_rank")
    private Integer sourceRank;

    protected VideoSnapshot() {
    }

    public VideoSnapshot(
            Video video,
            OffsetDateTime collectedAt,
            long viewCount,
            Long likeCount,
            Long commentCount,
            String sourceRegion,
            String sourceCategory,
            Integer sourceRank
    ) {
        this.video = video;
        this.collectedAt = collectedAt;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.sourceRegion = sourceRegion;
        this.sourceCategory = sourceCategory;
        this.sourceRank = sourceRank;
    }

    public Long getId() {
        return id;
    }

    public Video getVideo() {
        return video;
    }

    public OffsetDateTime getCollectedAt() {
        return collectedAt;
    }

    public long getViewCount() {
        return viewCount;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public String getSourceRegion() {
        return sourceRegion;
    }

    public String getSourceCategory() {
        return sourceCategory;
    }

    public Integer getSourceRank() {
        return sourceRank;
    }
}
