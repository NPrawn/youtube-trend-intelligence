package com.jeong.youtubetrend.common.response;

import java.time.OffsetDateTime;

public record ApiErrorResponse(
        OffsetDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {

    public static ApiErrorResponse of(int status, String error, String message, String path) {
        return new ApiErrorResponse(
                OffsetDateTime.now(),
                status,
                error,
                message,
                path
        );
    }
}
