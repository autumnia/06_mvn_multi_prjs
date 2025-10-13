package com.autumnia.shopsystem.order.service.domain;

import com.autumnia.shopsystem.order.service.domain.entity.Order;
import com.autumnia.shopsystem.order.service.domain.entity.Restaurant;
import com.autumnia.shopsystem.order.service.domain.entity.Product;
import com.autumnia.shopsystem.order.service.domain.event.OrderCancelledEvent;
import com.autumnia.shopsystem.order.service.domain.event.OrderCreatedEvent;
import com.autumnia.shopsystem.order.service.domain.event.OrderPaidEvent;
import com.autumnia.shopsystem.order.service.domain.exception.OrderDomainException;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
public class OrderDomainServiceImpl implements  OrderDomainService {
    private static final String UTC = "UTC";

    @Override
    public OrderCreatedEvent validate_and_initiate_order(Order order, Restaurant restaurant) {
        validate_restaurant( restaurant );
        set_order_product_information(order, restaurant);

        order.validate_order();
        order.initialize_order();
        log.info("Order with id:{} is initiated ", order.getId().getValue() );

        return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    private void set_order_product_information(Order order, Restaurant restaurant) {
        order.getItems().forEach(orderItem -> restaurant.getProducts().forEach(restaurantProduct -> {
            Product currentProduct = orderItem.getProduct();
            if (currentProduct.equals(restaurantProduct)) {
                currentProduct.update_with_confirmedName_and_price(restaurantProduct.getName(), restaurantProduct.getPrice());
            }
        }));
    }

    private void validate_restaurant(Restaurant restaurant) {
        if ( !restaurant.isActive() ) {
            throw new OrderDomainException( "restaurant with id: " + restaurant.getId() + " is currently not active");
        }


    }

    @Override
    public OrderPaidEvent pay_order(Order order) {
        order.pay();
        log.info( "Order with id: {} is paid", order.getId().getValue());
        return new OrderPaidEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void approve_order(Order order) {
        order.approve();
        log.info( "Order with id: {} is approved", order.getId().getValue() );
    }

    @Override
    public OrderCancelledEvent cancel_order_payment(Order order, List<String> failureMessage) {
        order.initCancel( failureMessage );
        log.info("Order payment is cacelling for order id: {}", order.getId().getValue() );
        return new OrderCancelledEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void cancel_order(Order order, List<String> failureMessage) {
        order.cancel(failureMessage);
        log.info("Order with id: {}", order.getId().getValue());

    }
}
