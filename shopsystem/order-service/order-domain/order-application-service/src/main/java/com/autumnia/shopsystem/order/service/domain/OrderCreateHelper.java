package com.autumnia.shopsystem.order.service.domain;

import com.autumnia.shopsystem.order.service.domain.dto.create.CreateOrderCommand;
import com.autumnia.shopsystem.order.service.domain.entity.Customer;
import com.autumnia.shopsystem.order.service.domain.entity.Order;
import com.autumnia.shopsystem.order.service.domain.entity.Restaurant;
import com.autumnia.shopsystem.order.service.domain.event.OrderCreatedEvent;
import com.autumnia.shopsystem.order.service.domain.exception.OrderDomainException;
import com.autumnia.shopsystem.order.service.domain.mapper.OrderDataMapper;
import com.autumnia.shopsystem.order.service.domain.ports.output.CustomerRepository;
import com.autumnia.shopsystem.order.service.domain.ports.output.OrderRepository;
import com.autumnia.shopsystem.order.service.domain.ports.output.RestaurantRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Component
public class OrderCreateHelper {
    private final OrderDomainService orderDomainService;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderDataMapper orderDataMapper;

    public OrderCreatedEvent persistOrder(CreateOrderCommand createOrderCommand) {
        // 고객확인
        check_customer(createOrderCommand.getCustomerId());

        // 음식점 확인
        Restaurant restaurant = check_restaurant( createOrderCommand );

        Order order = orderDataMapper.createOrderCommandToOrder( createOrderCommand );
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validate_and_initiate_order( order, restaurant);
        save_order(order);
        log.info("Order is created with id: {}", orderCreatedEvent.getOrder().getId().getValue() );

        return orderCreatedEvent;
    }

    private Restaurant check_restaurant(CreateOrderCommand createOrderCommand) {
        Restaurant restaurant = orderDataMapper.createOrderCommandToRestaurant(createOrderCommand);
        Optional<Restaurant> restaurantData = restaurantRepository.findRestaurantInformation(restaurant);

        if ( restaurantData.isEmpty() ) {
            log.warn("Could not find restaurant with restaurant id: {}", createOrderCommand.getRestaurantId() );
            throw new OrderDomainException("Could not find restaurant with restaurant id : " + createOrderCommand.getRestaurantId());
        }

        return restaurantData.get();
    }

    private void check_customer(@NotNull UUID customerId) {
        Optional<Customer> customer = this.customerRepository.findCustomer(customerId);
        if ( customer.isEmpty() ) {
            log.warn("Could not find customer with customer id: {}", customerId);
            throw new OrderDomainException("Could not find customer with customer id : " + customerId);
        }
    }

    private Order save_order(Order order) {
        Order saved_order = orderRepository.save(order);
        if ( saved_order == null ) {
            log.error("Could not save order.");
            throw new OrderDomainException("Could not save order.");
        }
        log.info ( "Order is saved with id: {} ", saved_order.getId() );
        return saved_order;
    }
}
