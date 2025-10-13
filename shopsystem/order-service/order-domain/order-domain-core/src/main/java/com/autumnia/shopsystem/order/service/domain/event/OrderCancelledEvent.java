package com.autumnia.shopsystem.order.service.domain.event;

import com.autumnia.shopsystem.domain.event.DomainEvent;
import com.autumnia.shopsystem.order.service.domain.entity.Order;

import java.time.ZonedDateTime;

public class OrderCancelledEvent  extends OrderEvent {

    public OrderCancelledEvent(Order order, ZonedDateTime createdAt) {
        super(order, createdAt);
    }
}
