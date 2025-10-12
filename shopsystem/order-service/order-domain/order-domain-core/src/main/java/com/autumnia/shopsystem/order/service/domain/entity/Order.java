package com.autumnia.shopsystem.order.service.domain.entity;

import com.autumnia.shopsystem.domain.entity.AggregateRoot;
import com.autumnia.shopsystem.domain.vo.*;
import com.autumnia.shopsystem.order.service.domain.exception.OrderDomainException;
import com.autumnia.shopsystem.order.service.domain.vo.OrderItemId;
import com.autumnia.shopsystem.order.service.domain.vo.StreetAddress;
import com.autumnia.shopsystem.order.service.domain.vo.TrackingId;

import java.util.List;
import java.util.UUID;

public class Order extends AggregateRoot<OrderId> {
    private final CustomerId customerId;
    private final RestaurantId restaurantId;
    private final StreetAddress deliveryAddress;
    private final Money price;
    private final List<OrderItem> items;

    private TrackingId trackingId;
    private OrderStatus orderStatus;
    private final List<String> failureMessages;

    public void initialize_order() {
        setId( new OrderId(UUID.randomUUID()) );
        trackingId = new TrackingId( UUID.randomUUID() );
        orderStatus = OrderStatus.PENDING;
        initialize_order_items();
    }

    public void initialize_order_items() {
        long item_id = 1;
        for ( OrderItem orderItem: items ) {
            orderItem.initailize_order_item( super.getId(), new OrderItemId(item_id++) );
        }
    }

    public void validate_order() {
        validate_initialize_order();
        validate_total_price();
        validate_items_price();
    }

    private void validate_items_price() {
        Money items_price_total = items.stream()
                .map(orderItem -> {
                    validate_item_price(orderItem);
                    return orderItem.getSubTotal();
                })
                .reduce(Money.zero, Money::add);

        if ( !this.price.equals( items_price_total ) ) {
            throw new OrderDomainException("전체가격: " + this.price.getAmount() + " 아이템 전체 합산: " + items_price_total.getAmount() + "과 같지 않습니다.");
        }
    }

    private void validate_item_price(OrderItem orderItem) {
        if ( !orderItem.isPriceValid() ) {
            throw new OrderDomainException(
                    "Order item price: " + this.price.getAmount()
                + " is not equal to order items total: " + orderItem.getProduct().getId().getValue()
            );
        }
    }

    private void validate_total_price() {
        if ( this.price == null || !this.price.is_greater_than_zero() ) {
            throw new OrderDomainException("합계는 0 보다 커야 한다.");
        }
    }

    private void validate_initialize_order() {
        if ( getId() != null || orderStatus != null ) {
            throw new OrderDomainException( "주문 초기화 에러");
        }
     }

    private Order(Builder builder) {
//        orderId = builder.orderId;
        super.setId( builder.orderId );
        customerId = builder.customerId;
        restaurantId = builder.restaurantId;
        deliveryAddress = builder.deliveryAddress;
        price = builder.price;
        items = builder.items;
        trackingId = builder.trackingId;
        orderStatus = builder.orderStatus;
        failureMessages = builder.failureMessages;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

    public StreetAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public Money getPrice() {
        return price;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public TrackingId getTrackingId() {
        return trackingId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<String> getFailureMessages() {
        return failureMessages;
    }

    public static final class Builder {
        private OrderId orderId;
        private CustomerId customerId;
        private RestaurantId restaurantId;
        private StreetAddress deliveryAddress;
        private Money price;
        private List<OrderItem> items;
        private TrackingId trackingId;
        private OrderStatus orderStatus;
        private List<String> failureMessages;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder orderId(OrderId val) {
            orderId = val;
            return this;
        }

        public Builder customerId(CustomerId val) {
            customerId = val;
            return this;
        }

        public Builder restaurantId(RestaurantId val) {
            restaurantId = val;
            return this;
        }

        public Builder deliveryAddress(StreetAddress val) {
            deliveryAddress = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder items(List<OrderItem> val) {
            items = val;
            return this;
        }

        public Builder trackingId(TrackingId val) {
            trackingId = val;
            return this;
        }

        public Builder orderStatus(OrderStatus val) {
            orderStatus = val;
            return this;
        }

        public Builder failureMessages(List<String> val) {
            failureMessages = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
