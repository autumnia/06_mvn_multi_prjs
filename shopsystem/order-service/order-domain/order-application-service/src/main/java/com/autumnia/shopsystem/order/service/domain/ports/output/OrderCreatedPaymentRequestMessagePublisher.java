package com.autumnia.shopsystem.order.service.domain.ports.output;

import com.autumnia.shopsystem.domain.event.DomainEventPublisher;
import com.autumnia.shopsystem.order.service.domain.event.OrderCreatedEvent;

public interface OrderCreatedPaymentRequestMessagePublisher  extends DomainEventPublisher<OrderCreatedEvent> {

}
