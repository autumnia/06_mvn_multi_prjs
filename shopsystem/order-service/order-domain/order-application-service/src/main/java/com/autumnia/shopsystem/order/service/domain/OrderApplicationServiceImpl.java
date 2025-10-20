package com.autumnia.shopsystem.order.service.domain;

import com.autumnia.shopsystem.order.service.domain.dto.create.CreateOrderCommand;
import com.autumnia.shopsystem.order.service.domain.dto.create.CreateOrderResponse;
import com.autumnia.shopsystem.order.service.domain.dto.track.TrackOrderQuery;
import com.autumnia.shopsystem.order.service.domain.dto.track.TrackOrderResponse;
import com.autumnia.shopsystem.order.service.domain.ports.input.OrderApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@RequiredArgsConstructor
@Slf4j
@Validated
@Service
class OrderApplicationServiceImpl implements OrderApplicationService  {

    private final OrderCreateCommandHandler orderCreateCommandHandler;
    private final OrderTrackCommandHandler orderTrackCommandHandler;

    @Override
    public CreateOrderResponse creatOrder(CreateOrderCommand createOrderCommand) {
        return this.orderCreateCommandHandler.createOrder(createOrderCommand);
    }

    @Override
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        return this.orderTrackCommandHandler.trackOrder(trackOrderQuery);
    }
}
