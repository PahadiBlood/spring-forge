package xyz.manojraw.dto.order;

import xyz.manojraw.enumeration.OrderStatus;

import java.time.Instant;

public record OrderListResponse(
        String orderId,
        OrderStatus status,
        Instant createdAt
) {
}
