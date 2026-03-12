package xyz.manojraw.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import xyz.manojraw.dto.order.OrderRequest;
import xyz.manojraw.dto.order.OrderResponse;
import xyz.manojraw.entity.Order;
import xyz.manojraw.entity.OrderItem;
import xyz.manojraw.entity.User;
import xyz.manojraw.exception.ApiException;
import xyz.manojraw.repository.OrderRepository;
import xyz.manojraw.repository.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public List<OrderResponse> saveAll(List<OrderRequest> orderRequests) {
        List<Order> orders = orderRequests
                .stream()
                .map(this::toEntity).toList();

        orderRepository.saveAll(orders);
        return orders
                .stream()
                .map(this::toDto)
                .toList();
    }

    private OrderResponse toDto(Order order) {
        if (order == null) return null;

        List<OrderResponse.OrderItemResponse> itemDtos = order.getItems().stream()
                .map(item -> new OrderResponse.OrderItemResponse(
                        item.getId(),
                        item.getName(),
                        item.getQuantity()
                ))
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getOrderId(),
                order.getStatus(),
                order.getUser() != null ? order.getUser().getFullName() : null,
                itemDtos,
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }

    private Order toEntity(OrderRequest orderRequest) {
        if (orderRequest == null) return null;
        User user = userRepository
                .findById(orderRequest.userId())
                .orElseThrow(() -> new ApiException("User not found", HttpStatus.FORBIDDEN));

        Order order = new Order();
        order.setUser(user);
        order.setStatus(orderRequest.status());
        order.setOrderId("ORD-" + System.currentTimeMillis());

        if (orderRequest.items() != null) {
            List<OrderItem> entities = orderRequest.items().stream()
                    .map(itemDto -> {
                        OrderItem item = new OrderItem();
                        item.setName(itemDto.name());
                        item.setQuantity(itemDto.quantity());
                        item.setOrder(order);
                        return item;
                    })
                    .toList();
            order.setItems(entities);
        }

        return order;
    }
}
