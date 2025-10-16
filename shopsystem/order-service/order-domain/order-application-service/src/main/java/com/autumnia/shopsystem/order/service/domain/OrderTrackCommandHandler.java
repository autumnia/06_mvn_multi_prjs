package com.autumnia.shopsystem.order.service.domain;

import com.autumnia.shopsystem.order.service.domain.dto.track.TrackOrderQuery;
import com.autumnia.shopsystem.order.service.domain.dto.track.TrackingOrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderTrackCommandHandler {
    public TrackingOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        return null;
    }
}
