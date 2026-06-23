package com.api.ecommerce.orders.application;

import com.api.ecommerce.cart.domain.Cart;
import com.api.ecommerce.orders.domain.Order;
import com.api.ecommerce.orders.domain.OrderDetail;
import com.api.ecommerce.orders.domain.OrderStatus;
import com.api.ecommerce.orders.infrastructure.persistence.IOrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService implements IOrderService{

    private IOrderRepository orderRepository;

    public OrderService(IOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrderFromCart(Cart cart) {
        Order order = new Order();
        List<OrderDetail> orderDetails = cart.getItems().stream()
                .map(cartItem ->
                        new OrderDetail(
                                null,
                                cartItem.getQuantity(),
                                cartItem.getProduct().getUnitPrice(),
                                cartItem.getProduct().getUnitPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())),
                                order,
                                cartItem.getProduct()
                        )
                )
                .toList();

        BigDecimal total = orderDetails.stream()
                .map(OrderDetail::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotal(total);
        order.setOrderDetails(orderDetails);
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        orderRepository.save(order);
        return order;
    }

    @Override
    public Order getOrCreateOrder(Long userId, Cart cart) {
        return orderRepository
                .findByUserIdAndStatus(userId,OrderStatus.PENDING)
                .orElseGet(() -> createOrderFromCart(cart));
    }
}
