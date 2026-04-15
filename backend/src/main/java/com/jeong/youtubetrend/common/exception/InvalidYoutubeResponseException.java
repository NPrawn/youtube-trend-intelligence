package com.jeong.youtubetrend.common.exception;

public class InvalidYoutubeResponseException extends RuntimeException {

    public InvalidYoutubeResponseException(String message) {
        super(message);
    }

    public InvalidYoutubeResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}
