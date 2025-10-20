package com.autumnia.shopsystem.order.service.domain;

import com.autumnia.shopsystem.order.service.domain.dto.track.TrackOrderQuery;
import com.autumnia.shopsystem.order.service.domain.dto.track.TrackOrderResponse;
import com.autumnia.shopsystem.order.service.domain.entity.Order;
import com.autumnia.shopsystem.order.service.domain.exception.OrderNotFoundException;
import com.autumnia.shopsystem.order.service.domain.mapper.OrderDataMapper;
import com.autumnia.shopsystem.order.service.domain.ports.output.OrderRepository;
import com.autumnia.shopsystem.order.service.domain.vo.TrackingId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Component
public class OrderTrackCommandHandler {
    private final OrderDataMapper orderDataMapper;
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        Optional<Order> order_from_db = this.orderRepository.findByTrackingId( new TrackingId(trackOrderQuery.getOrderTrackingId()) );

        if ( order_from_db.isEmpty() ) {
            log.warn("Could not find order with trackingId: {}", trackOrderQuery.getOrderTrackingId());
            throw new OrderNotFoundException( "Could not find order with trackingId: " + trackOrderQuery.getOrderTrackingId() );
        }

        return orderDataMapper.orderToTrackOrderResponse( order_from_db.get() );
    }
}
