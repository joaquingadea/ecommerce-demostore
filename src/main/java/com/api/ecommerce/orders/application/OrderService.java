package com.api.ecommerce.orders.application;

import com.api.ecommerce.cart.domain.Cart;
import com.api.ecommerce.orders.domain.Order;
import com.api.ecommerce.orders.domain.OrderDetail;
import com.api.ecommerce.orders.domain.OrderStatus;
import com.api.ecommerce.orders.dto.response.MyOrderDTO;
import com.api.ecommerce.orders.dto.response.MyOrderDetailsDTO;
import com.api.ecommerce.orders.dto.response.OrderDTO;
import com.api.ecommerce.orders.dto.response.OrderDetailDTO;
import com.api.ecommerce.orders.infrastructure.persistence.IOrderDetailRepository;
import com.api.ecommerce.orders.infrastructure.persistence.IOrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class OrderService implements IOrderService{

    private IOrderRepository orderRepository;
    private IOrderDetailRepository orderDetailRepository;

    public OrderService(IOrderRepository orderRepository, IOrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
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
    @Override
    public Page<OrderDTO> getOrders(Pageable ordersPageable) {
        return orderRepository.findAllProjectedBy(ordersPageable);
    }

    @Override
    public List<OrderDetailDTO> getOrderDetails(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

    @Override
    public Page<MyOrderDTO> getMyOrders(Long userId, Pageable pageRequest) {
        return orderRepository.findAllByUserId(userId,pageRequest);
    }

    @Override
    public MyOrderDTO getMyPendingOrder(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public List<MyOrderDetailsDTO> getMyOrderDetails(Long orderId, Long userId) {
        return orderDetailRepository.findByOrderIdAndUserId(orderId,userId);
    }
}
