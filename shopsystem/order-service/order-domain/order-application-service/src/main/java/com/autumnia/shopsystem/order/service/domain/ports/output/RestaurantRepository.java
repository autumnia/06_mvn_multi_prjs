package com.autumnia.shopsystem.order.service.domain.ports.output;

import com.autumnia.shopsystem.order.service.domain.entity.Restaurant;

import java.util.Optional;

public interface RestaurantRepository {
    Optional<Restaurant> findRestaurantInformation(Restaurant restaurant);

}
