package xyz.manojraw.kafka;

public record OrderNotificationEvent(
        String title,
        String message,
        String orderId
) {
}
