package com.autumnia.shopsystem.order.service.domain.ports.output;

import com.autumnia.shopsystem.order.service.domain.entity.Order;
import com.autumnia.shopsystem.order.service.domain.vo.TrackingId;

import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findByTrackingId(TrackingId trackingId);
}
