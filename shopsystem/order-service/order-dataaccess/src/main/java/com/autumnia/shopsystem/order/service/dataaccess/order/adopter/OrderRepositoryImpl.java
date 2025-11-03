package com.autumnia.shopsystem.order.service.dataaccess.order.adopter;

import com.autumnia.shopsystem.order.service.dataaccess.order.entity.OrderEntity;
import com.autumnia.shopsystem.order.service.dataaccess.order.mapper.OrderDataAccessMapper;
import com.autumnia.shopsystem.order.service.dataaccess.order.repository.OrderJpaRepository;
import com.autumnia.shopsystem.order.service.domain.entity.Order;
import com.autumnia.shopsystem.order.service.domain.ports.output.OrderRepository;
import com.autumnia.shopsystem.order.service.domain.vo.TrackingId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class OrderRepositoryImpl implements OrderRepository {
    // JpaRepository
    private final OrderJpaRepository orderJpaRepository;
    private final OrderDataAccessMapper orderDataAccessMapper;

    @Override
    public Order save(Order order) {
        return orderDataAccessMapper.orderEntityToOrder(
                orderJpaRepository.save( orderDataAccessMapper.orderToOrderEntity(order) )
        );
    }

    @Override
    public Optional<Order> findByTrackingId(TrackingId trackingId) {
        return orderJpaRepository
                .findByTrackingId( trackingId.getValue() )
                .map(orderDataAccessMapper::orderEntityToOrder);
    }
}
