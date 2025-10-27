package com.autumnia.shopsystem.order.service.domain.entity;

import com.autumnia.shopsystem.domain.entity.BaseEntity;
import com.autumnia.shopsystem.domain.vo.Money;
import com.autumnia.shopsystem.domain.vo.ProductId;
import lombok.ToString;


@ToString
public class Product extends BaseEntity<ProductId> {
    private String name;
    private Money price;

    public Product(ProductId productId, String name, Money price) {
        super.setId( productId);
        this.name = name;
        this.price = price;
    }

    public Product(ProductId productId) {
        super.setId( productId );
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

    public void update_with_confirmedName_and_price( String name, Money price) {
        this.name = name;
        this.price = price;
    }
}
