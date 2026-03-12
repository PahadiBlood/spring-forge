package xyz.manojraw.dto.order;

import xyz.manojraw.enumeration.OrderStatus;
import java.time.Instant;
import java.util.List;

public record OrderResponse(
        Long id,
        String orderId,
        OrderStatus status,
        String fullName,
        List<OrderItemResponse> items,
        Instant createdAt,
        Instant updatedAt
) {
    public record OrderItemResponse(
            Long id,
            String name,
            int quantity
    ) {
    }
}
