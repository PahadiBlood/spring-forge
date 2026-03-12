package xyz.manojraw.exception;

import java.time.Instant;

public record ApiError(
        int code,
        String status,
        String message,
        Instant timeStamp
) {
}
