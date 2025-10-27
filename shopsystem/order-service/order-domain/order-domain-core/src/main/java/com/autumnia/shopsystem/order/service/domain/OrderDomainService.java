package com.autumnia.shopsystem.order.service.domain;

import com.autumnia.shopsystem.order.service.domain.entity.Order;
import com.autumnia.shopsystem.order.service.domain.entity.Restaurant;
import com.autumnia.shopsystem.order.service.domain.event.OrderCancelledEvent;
import com.autumnia.shopsystem.order.service.domain.event.OrderCreatedEvent;
import com.autumnia.shopsystem.order.service.domain.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {
    OrderCreatedEvent validate_and_initiate_order(Order order, Restaurant restaurant);
    OrderPaidEvent pay_order(Order order);
    void approve_order(Order order);
    OrderCancelledEvent cancel_order_payment(Order order, List<String> failureMessage);
   void cancel_order(Order order, List<String> failureMessage);
}
