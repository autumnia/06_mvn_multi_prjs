package com.autumnia.shopsystem.order.service.dataaccess.order.mapper;

import com.autumnia.shopsystem.domain.vo.CustomerId;
import com.autumnia.shopsystem.domain.vo.Money;
import com.autumnia.shopsystem.domain.vo.OrderId;
import com.autumnia.shopsystem.domain.vo.RestaurantId;
import com.autumnia.shopsystem.order.service.dataaccess.order.entity.OrderAddressEntity;
import com.autumnia.shopsystem.order.service.dataaccess.order.entity.OrderEntity;
import com.autumnia.shopsystem.order.service.dataaccess.order.entity.OrderItemEntity;
import com.autumnia.shopsystem.order.service.domain.entity.Order;
import com.autumnia.shopsystem.order.service.domain.entity.OrderItem;
import com.autumnia.shopsystem.order.service.domain.vo.StreetAddress;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.autumnia.shopsystem.order.service.domain.entity.Order.FAILURE_MESSAGE_DELIMETER;

@Component
public class OrderDataAccessMapper {
    public Order orderEntityToOrder(OrderEntity orderEntity) {

        return Order.builder()
                .orderId( new OrderId(orderEntity.getId()) )
                .customerId( new CustomerId(orderEntity.getCustomerId()))
                .restaurantId( new RestaurantId(orderEntity.getRestaurantId()))
                .deliveryAddress( addressEntityToDeliveryAddress(orderEntity.getAddress()) )
                .price(new Money(orderEntity.getPrice()) )
                .items( orderItemEntitiesToOrderItems(orderEntity.getItems()) )
                .build();
    }

    private List<OrderItem> orderItemEntitiesToOrderItems(List<OrderItemEntity> items) {
        return null;
    }

    private StreetAddress addressEntityToDeliveryAddress(OrderAddressEntity address) {
        return new StreetAddress(
                address.getId(),
                address.getStreet(),
                address.getPostCode(),
                address.getCity()
        );
    }


    public OrderEntity orderToOrderEntity(Order order) {
        OrderEntity orderEntity = OrderEntity.builder()
                .id(order.getId().getValue())
                .customerId(order.getCustomerId().getValue())
                .restaurantId(order.getRestaurantId().getValue())
                .trackingid(order.getTrackingId().getValue())
                .address(DeliveryAddressToAddressEntity( order.getDeliveryAddress() ))
                .price( order.getPrice().getAmount() )
                .items(  orderItemsToOrderItemEntities( order.getItems() )  )
                .orderStatus(order.getOrderStatus())
                .failureMessage(order.getFailureMessages() != null ?  String.join(FAILURE_MESSAGE_DELIMETER, order.getFailureMessages()) : "" )
                .build();

        // 빠트리지 않도록 주의
        orderEntity.getAddress().setOrder(orderEntity);
        orderEntity.getItems().forEach(orderItemEntity ->   orderItemEntity.setOrder(orderEntity) );

        return orderEntity;
    }

    private List<OrderItemEntity> orderItemsToOrderItemEntities(List<OrderItem> items) {
        return items.stream()
                .map( orderItem ->  OrderItemEntity.builder()
                        .id (  orderItem.getId().getValue() )
                        .productId( orderItem.getProduct().getId().getValue() )
                        .price(orderItem.getPrice().getAmount())
                        .quantity(orderItem.getQuantity())
                        .subTotal(orderItem.getSubTotal().getAmount())
                        .build()
                )
                .toList();
    }

    private OrderAddressEntity DeliveryAddressToAddressEntity(StreetAddress deliveryAddress) {
        return OrderAddressEntity.builder()
                .id(deliveryAddress.getId())
                .street(deliveryAddress.getStreet())
                .postCode(deliveryAddress.getPostalCodee())
                .city(deliveryAddress.getCity())
                .build();
    }
}
