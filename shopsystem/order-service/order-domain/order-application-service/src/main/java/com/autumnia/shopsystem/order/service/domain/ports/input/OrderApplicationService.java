package com.autumnia.shopsystem.order.service.domain.ports.input;

import com.autumnia.shopsystem.order.service.domain.dto.create.CreateOrderCommand;
import com.autumnia.shopsystem.order.service.domain.dto.create.CreateOrderResponse;
import com.autumnia.shopsystem.order.service.domain.dto.track.TrackOrderQuery;
import com.autumnia.shopsystem.order.service.domain.dto.track.TrackingOrderResponse;
import jakarta.validation.Valid;

public interface OrderApplicationService {
    CreateOrderResponse creatOrder(@Valid CreateOrderCommand createOrderCommand);
    TrackingOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);
}
