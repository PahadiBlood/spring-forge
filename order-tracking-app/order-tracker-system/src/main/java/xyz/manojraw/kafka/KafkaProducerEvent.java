package xyz.manojraw.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KafkaProducerEvent {

    private static final String TOPIC_NAME = "user-order-notification-topic";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendNotification(OrderNotificationEvent notificationEvent) {
        var notificationResponse = kafkaTemplate.send(TOPIC_NAME, notificationEvent);
        notificationResponse.whenComplete((res, ex) -> {
            if (res != null) {
                System.out.println("Event saved successfully " + notificationEvent.toString());
            } else if (ex != null) {
                ex.printStackTrace();
            }
        });
    }
}
