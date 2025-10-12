package com.autumnia.shopsystem.order.service.domain.entity;

import com.autumnia.shopsystem.domain.entity.BaseEntity;
import com.autumnia.shopsystem.domain.vo.Money;
import com.autumnia.shopsystem.domain.vo.OrderId;
import com.autumnia.shopsystem.order.service.domain.vo.OrderItemId;

public class OrderItem extends BaseEntity<OrderItemId> {
    private OrderId orderId;
    private final Product product;
    private final int quantity;
    private final Money price;
    private final Money subTotal;

    protected void initailize_order_item(OrderId orderId, OrderItemId orderItemId) {
        this.orderId = orderId;
        super.setId( orderItemId );
    }

    protected boolean isPriceValid() {
        return this.price.is_greater_than_zero()
                && this.price.equals( this.product.getPrice())
                && this.price.multiply( this.quantity ).equals( this.subTotal );
    }


    public OrderId getOrderId() {
        return orderId;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public Money getPrice() {
        return price;
    }

    public Money getSubTotal() {
        return subTotal;
    }

    private OrderItem(Builder builder) {
        super.setId( builder.orderItemId );
        product = builder.product;
        quantity = builder.quantity;
        price = builder.price;
        subTotal = builder.subTotal;
    }


    public static final class Builder {
        private OrderItemId orderItemId;
        private Product product;
        private int quantity;
        private Money price;
        private Money subTotal;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder OrderItemId(OrderItemId val) {
            orderItemId = val;
            return this;
        }

        public Builder product(Product val) {
            product = val;
            return this;
        }

        public Builder quantity(int val) {
            quantity = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder subTotal(Money val) {
            subTotal = val;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this);
        }
    }
}
