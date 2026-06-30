package com.api.ecommerce.orders.application;

import com.api.ecommerce.cart.domain.Cart;
import com.api.ecommerce.orders.domain.Order;
import com.api.ecommerce.orders.dto.response.MyOrderDTO;
import com.api.ecommerce.orders.dto.response.MyOrderDetailsDTO;
import com.api.ecommerce.orders.dto.response.OrderDTO;
import com.api.ecommerce.orders.dto.response.OrderDetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderService {
    Order getOrCreateOrder(Long userId,Cart cart);
    Page<OrderDTO> getOrders(Pageable ordersPageable);
    List<OrderDetailDTO> getOrderDetails(Long orderId);
    Page<MyOrderDTO> getMyOrders(Long userId, Pageable pageRequest);
    MyOrderDTO getMyPendingOrder(Long userId);
    List<MyOrderDetailsDTO> getMyOrderDetails(Long orderId, Long aLong);
}
