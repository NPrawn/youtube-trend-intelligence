package com.jeong.youtubetrend.video.application;

public enum VideoFormType {
    ALL,
    SHORT,
    LONG;

    public static VideoFormType from(String value) {
        if (value == null || value.isBlank()) {
            return ALL;
        }

        return switch (value.toLowerCase()) {
            case "all" -> ALL;
            case "short" -> SHORT;
            case "long" -> LONG;
            default -> throw new IllegalArgumentException("form must be one of: all, short, long");
        };
    }
}
