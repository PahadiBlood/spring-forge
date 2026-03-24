package xyz.manojraw.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.manojraw.dto.order.OrderListResponse;
import xyz.manojraw.dto.order.OrderRequest;
import xyz.manojraw.dto.order.OrderResponse;
import xyz.manojraw.enumeration.OrderStatus;
import xyz.manojraw.service.impl.OrderServiceImpl;

import java.util.List;

@Tag(name = "Order", description = "Handles order operations")

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderServiceImpl orderService;

    @GetMapping
    public ResponseEntity<Page<OrderListResponse>> getAll(@RequestParam Long userId, @RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(orderService.getAll(userId, pageNo, size));
    }

    @PostMapping
    public ResponseEntity<List<OrderResponse>> saveAll(@RequestBody List<OrderRequest> orderRequests) {
        return ResponseEntity.ok(orderService.saveAll(orderRequests));
    }

    @PatchMapping
    public ResponseEntity<Void> updateStatus(@RequestParam String orderId, @RequestParam OrderStatus orderStatus){
        orderService.updateStatus(orderId, orderStatus);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getNotification(@RequestParam Long sleepTime){
        return ResponseEntity.ok(orderService.getNotification(sleepTime));
    }
}