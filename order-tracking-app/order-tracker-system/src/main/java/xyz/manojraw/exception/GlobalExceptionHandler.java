package xyz.manojraw.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiError> handleApiException(ApiException ex) {
        ex.printStackTrace();
        ApiError error = new ApiError(
                ex.getStatus().value(),
                ex.getStatus().toString(),
                ex.getMessage(),
                Instant.now()
        );
        log.error("Error in app {}", error, ex);
        return ResponseEntity.status(ex.getStatus()).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception ex) {
        ex.printStackTrace();
        ApiError error = new ApiError(
                HttpStatus.INSUFFICIENT_STORAGE.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                ex.getMessage(),
                Instant.now()
        );
        log.error("Error in app {}", error, ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
