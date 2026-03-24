package xyz.manojraw.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import xyz.manojraw.dto.order.OrderListResponse;
import xyz.manojraw.entity.Order;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT " +
                "new xyz.manojraw.dto.order.OrderListResponse(" +
                "o.orderId," +
                " o.status," +
                " o.createdAt) " +
            "FROM Order o " +
            "WHERE o.user.id = :userId"
    )
    Page<OrderListResponse> findAllByUserId(Long userId, Pageable pageable);

    Optional<Order> findByOrderId(String orderId);
}
