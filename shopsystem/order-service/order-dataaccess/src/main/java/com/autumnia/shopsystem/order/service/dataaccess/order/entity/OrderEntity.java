package com.autumnia.shopsystem.order.service.dataaccess.order.entity;

import com.autumnia.shopsystem.domain.vo.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Table(name="orders")
@Entity
public class OrderEntity {
    @Id
    private UUID id;
    private UUID customerId;
    private UUID restaurantId;
    private UUID trackingid;

    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private String failureMessage;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private OrderAddressEntity address;

    @OneToMany( mappedBy = "item", cascade = CascadeType.ALL)
    private List<OrderItemEntity> items;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
