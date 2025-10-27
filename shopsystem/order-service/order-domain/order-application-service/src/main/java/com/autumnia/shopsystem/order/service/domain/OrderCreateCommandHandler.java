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
        log.info("주문 id {}:", orderCreatedEvent.getOrder().getId().getValue() );
        log.info("고객 id {}:", orderCreatedEvent.getOrder().getCustomerId().getValue() );
        log.info("상품 id {}:", orderCreatedEvent.getOrder().getItems().getLast().getProduct().getId().getValue() );
        log.info("식당 id {}:", orderCreatedEvent.getOrder().getRestaurantId().getValue() );
        log.info("추적 id {}:", orderCreatedEvent.getOrder().getTrackingId().getValue() );


        orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent);

        return orderDataMapper.orderToCreateOrderResponse( orderCreatedEvent.getOrder() , "Order created successfully");
    }

}
