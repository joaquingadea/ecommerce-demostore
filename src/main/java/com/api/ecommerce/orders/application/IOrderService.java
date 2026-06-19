package com.api.ecommerce.orders.application;

import com.api.ecommerce.cart.domain.Cart;
import com.api.ecommerce.orders.domain.Order;

public interface IOrderService {
    Order createOrderFromCart(Cart cart);
}
