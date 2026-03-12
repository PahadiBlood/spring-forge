package xyz.manojraw.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import xyz.manojraw.base.BaseEntity;


@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "orders_items", indexes = {
        @Index(name = "idx_orders_items_order_id", columnList = "order_id")
})
public class OrderItem extends BaseEntity {
    private String name;
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
}
