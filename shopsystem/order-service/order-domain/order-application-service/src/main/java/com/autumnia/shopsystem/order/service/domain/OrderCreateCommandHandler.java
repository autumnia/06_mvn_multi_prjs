package com.autumnia.shopsystem.order.service.domain;

import com.autumnia.shopsystem.domain.vo.OrderStatus;
import com.autumnia.shopsystem.order.service.domain.dto.create.CreateOrderCommand;
import com.autumnia.shopsystem.order.service.domain.dto.create.CreateOrderResponse;
import com.autumnia.shopsystem.order.service.domain.entity.Customer;
import com.autumnia.shopsystem.order.service.domain.entity.Order;
import com.autumnia.shopsystem.order.service.domain.entity.Restaurant;
import com.autumnia.shopsystem.order.service.domain.event.OrderCreatedEvent;
import com.autumnia.shopsystem.order.service.domain.exception.OrderDomainException;
import com.autumnia.shopsystem.order.service.domain.mapper.OrderDataMapper;
import com.autumnia.shopsystem.order.service.domain.ports.output.CustomerRepository;
import com.autumnia.shopsystem.order.service.domain.ports.output.OrderCreatedPaymentRequestMessagePublisher;
import com.autumnia.shopsystem.order.service.domain.ports.output.OrderRepository;
import com.autumnia.shopsystem.order.service.domain.ports.output.RestaurantRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Component
public class OrderCreateCommandHandler {
    private final OrderCreateHelper orderCreateHelper;
    private final OrderDataMapper orderDataMapper;
    private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;

    @Transactional
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        OrderCreatedEvent orderCreatedEvent = orderCreateHelper.persistOrder(createOrderCommand);
        log.info("Order is created with id {}:", orderCreatedEvent.getOrder().getId().getValue() );

        orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent);

        return orderDataMapper.orderToCreateOrderResponse( orderCreatedEvent.getOrder() );
    }

}
