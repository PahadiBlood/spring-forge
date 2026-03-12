package xyz.manojraw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.manojraw.dto.order.OrderRequest;
import xyz.manojraw.dto.order.OrderResponse;
import xyz.manojraw.service.impl.OrderServiceImpl;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderServiceImpl orderService;

    @PostMapping
    public ResponseEntity<List<OrderResponse>> saveAll(@RequestBody List<OrderRequest> orderRequests) {
        return ResponseEntity.ok(orderService.saveAll(orderRequests));
    }
}