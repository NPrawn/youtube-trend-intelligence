package com.jeong.youtubetrend.video.application;

public record CollectedChannel(
        String youtubeChannelId,
        String title,
        Long subscriberCount,
        boolean hiddenSubscriberCount,
        long viewCount,
        long videoCount
) {

}
