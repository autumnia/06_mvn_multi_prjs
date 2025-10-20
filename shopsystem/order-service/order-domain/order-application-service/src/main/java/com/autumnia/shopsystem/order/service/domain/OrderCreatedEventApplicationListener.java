package com.autumnia.shopsystem.order.service.domain;

import com.autumnia.shopsystem.order.service.domain.event.OrderCreatedEvent;
import com.autumnia.shopsystem.order.service.domain.ports.output.OrderCreatedPaymentRequestMessagePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Slf4j
@Component
public class OrderCreatedEventApplicationListener {
    private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;

    // createOrder 메서스가 완료됙 트랜잭선이 완료될 때만 처리됩니다
    @TransactionalEventListener
    void process(OrderCreatedEvent orderCreatedEvent) {
        orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent);
    }
}
