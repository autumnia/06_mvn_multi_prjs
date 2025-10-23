package com.autumnia.shopsystem.order.service.domain.mapper;

import com.autumnia.shopsystem.domain.vo.CustomerId;
import com.autumnia.shopsystem.domain.vo.Money;
import com.autumnia.shopsystem.domain.vo.ProductId;
import com.autumnia.shopsystem.domain.vo.RestaurantId;
import com.autumnia.shopsystem.order.service.domain.dto.create.CreateOrderCommand;
import com.autumnia.shopsystem.order.service.domain.dto.create.CreateOrderResponse;
import com.autumnia.shopsystem.order.service.domain.dto.create.OrderAddress;
import com.autumnia.shopsystem.order.service.domain.dto.track.TrackOrderResponse;
import com.autumnia.shopsystem.order.service.domain.entity.*;
import com.autumnia.shopsystem.order.service.domain.vo.StreetAddress;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class OrderDataMapper {
    public Restaurant createOrderCommandToRestaurant(CreateOrderCommand createOrderCommand) {

        return Restaurant.builder()
                .restaurantId( new RestaurantId( createOrderCommand.getRestaurantId() ))
                .products(createOrderCommand.getItems().stream().map( orderItem ->
                        new Product( new ProductId( orderItem.getProductId() ))
                    ).toList())
                .build();

    }

    public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand) {
        return Order.builder()
                .customerId( new CustomerId( createOrderCommand.getCustomerId() ))
                .restaurantId( new RestaurantId( createOrderCommand.getRestaurantId() ))
                .deliveryAddress( orderAddressToStreetAddress(createOrderCommand.getAddress()))
                .price(new Money(createOrderCommand.getPrice()))
                .items( orderItemsToOrderItemEntities( createOrderCommand.getItems() ))
                .build();
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order order, String message) {
        return CreateOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .message(message)
                .build();

    }

    public TrackOrderResponse orderToTrackOrderResponse(Order order) {
        return TrackOrderResponse.builder()
                .orderTackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .failureMessages(order.getFailureMessages())
                .build();
    }

    private List<OrderItem> orderItemsToOrderItemEntities(@NotNull List<com.autumnia.shopsystem.order.service.domain.dto.create.OrderItem> orderItems) {
        return orderItems.stream()
                .map( orderItem -> OrderItem.builder()
                    .product( new Product( new ProductId( orderItem.getProductId() ) ) )
                    .price( new Money( orderItem.getPrice() ) )
                    .quantity( orderItem.getQuantity() )
                    .subTotal( new Money( orderItem.getSubTotal() ) )
                    .build()
                )
                .toList();
    }

    private StreetAddress orderAddressToStreetAddress(@NotNull OrderAddress orderAddress) {
        return new StreetAddress(
                UUID.randomUUID(),
                orderAddress.getStreet(),
                orderAddress.getPostalCode(),
                orderAddress.getCity()
        );
    }
}

