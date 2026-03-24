package xyz.manojraw.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    @GetMapping
    public ResponseEntity<String> getDummyNotification(@RequestParam Long sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("""
                {
                    "id": 101,
                    "message": "Your Zazbaat post received a new comment!",
                    "type": "SOCIAL_ALERT",
                    "status": "UNREAD",
                    "createdAt": "2026-03-18T16:55:00Z",
                    "metadata": {
                        "senderId": "user_456",
                        "postId": "p_789"
                    }
                }
                """
        );
    }
}
