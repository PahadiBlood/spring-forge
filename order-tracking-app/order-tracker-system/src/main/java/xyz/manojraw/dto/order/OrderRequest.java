package xyz.manojraw.dto.order;

import xyz.manojraw.enumeration.OrderStatus;

import java.util.List;

public record OrderRequest(
        //get user from token in real project
        Long userId,
        OrderStatus status,
        List<OrderItemRequest> items
) {
    public record OrderItemRequest(
            String name,
            int quantity
    ) {
    }
}