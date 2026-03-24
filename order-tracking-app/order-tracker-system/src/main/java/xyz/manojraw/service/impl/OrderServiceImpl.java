package xyz.manojraw.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import xyz.manojraw.client.NotificationClient;
import xyz.manojraw.dto.order.OrderListResponse;
import xyz.manojraw.dto.order.OrderRequest;
import xyz.manojraw.dto.order.OrderResponse;
import xyz.manojraw.entity.Order;
import xyz.manojraw.entity.OrderItem;
import xyz.manojraw.entity.User;
import xyz.manojraw.enumeration.OrderStatus;
import xyz.manojraw.exception.ApiException;
import xyz.manojraw.kafka.KafkaProducerEvent;
import xyz.manojraw.kafka.OrderNotificationEvent;
import xyz.manojraw.repository.OrderRepository;
import xyz.manojraw.repository.UserRepository;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final KafkaProducerEvent event;
    private final NotificationClient client;

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

    public Page<OrderListResponse> getAll(Long userId, int pageNo, int size) {
        pageNo = (pageNo <= 0) ? 0 : (pageNo - 1);
        if (size < 1) size = 10;

        Pageable pageable = PageRequest.of(
                pageNo,
                size,
                Sort.by(Sort.Order.desc("id")
                )
        );
        return orderRepository.findAllByUserId(userId, pageable);
    }

    public void updateStatus(String orderId, OrderStatus status) {
        Order order = orderRepository
                .findByOrderId(orderId)
                .orElseThrow(() -> new ApiException("Order does not exists", HttpStatus.BAD_REQUEST));

        order.setStatus(status);
        orderRepository.save(order);

        //kafka notification
        sendNotificationEvent(order);
    }

    private void sendNotificationEvent(Order order) {
        // 1. Validation
        if (order == null || order.getStatus() == null) {
            return;
        }

        var status = order.getStatus().getDisplayName();
        var id = order.getOrderId();

        String title = "Order %s".formatted(status);
        String message = "Your Order %s has been %s".formatted(id, status);

        // 2. Create the event
        OrderNotificationEvent notificationEvent = new OrderNotificationEvent(
                title,
                message,
                id
        );

        event.sendNotification(notificationEvent);
    }

    public String getNotification(Long sleepTime) {
        return client.getLastNotification(sleepTime);
    }
}
