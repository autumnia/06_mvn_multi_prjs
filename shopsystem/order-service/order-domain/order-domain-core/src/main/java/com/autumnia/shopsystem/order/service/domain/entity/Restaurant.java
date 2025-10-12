package com.autumnia.shopsystem.order.service.domain.entity;

import com.autumnia.shopsystem.domain.entity.AggregateRoot;
import com.autumnia.shopsystem.domain.vo.RestaurantId;

import java.util.List;

public class Restaurant extends AggregateRoot<RestaurantId> {
    private boolean isActive;
    private final List<Product> products;

    private Restaurant(Builder builder) {
        super.setId( builder.restaurantId );
        isActive = builder.isActive;
        products = builder.products;
    }

    public boolean isActive() {
        return isActive;
    }

    public List<Product> getProducts() {
        return products;
    }


    public static final class Builder {
        private RestaurantId restaurantId;
        private boolean isActive;
        private List<Product> products;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder restaurantId(RestaurantId val) {
            restaurantId = val;
            return this;
        }

        public Builder isActive(boolean val) {
            isActive = val;
            return this;
        }

        public Builder products(List<Product> val) {
            products = val;
            return this;
        }

        public Restaurant build() {
            return new Restaurant(this);
        }
    }
}
