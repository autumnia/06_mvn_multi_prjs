package com.autumnia.shopsystem.order.service.application.rest;

import com.autumnia.shopsystem.order.service.domain.dto.create.CreateOrderCommand;
import com.autumnia.shopsystem.order.service.domain.dto.create.CreateOrderResponse;
import com.autumnia.shopsystem.order.service.domain.dto.track.TrackOrderQuery;
import com.autumnia.shopsystem.order.service.domain.dto.track.TrackOrderResponse;
import com.autumnia.shopsystem.order.service.domain.ports.input.OrderApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@Controller
@RequestMapping(value = "/orders", produces = "application/vnd.api.v1+json")
@RequiredArgsConstructor
public class OrderController {
    private final OrderApplicationService orderApplicationService;

    @PostMapping
    public ResponseEntity<CreateOrderResponse>  createOrder(@RequestBody  CreateOrderCommand createOrderCommand) {
        log.info("Creating order for customer: {} at restaurant {}", createOrderCommand.getCustomerId(), createOrderCommand.getRestaurantId());
        CreateOrderResponse createOrderResponse = orderApplicationService.createOrder(createOrderCommand);
        log.info("Order created with tracking id : {}", createOrderResponse.getOrderTrackingId());

        return ResponseEntity.ok( createOrderResponse );
    }

    @GetMapping("/{trackingId}")
    public ResponseEntity<TrackOrderResponse> getOrderByTackingId(@PathVariable  UUID trackingId) {
        TrackOrderQuery trackOrderQuery = TrackOrderQuery.builder()
                .orderTrackingId(trackingId)
                .build();
        TrackOrderResponse trackOrderResponse = orderApplicationService.trackOrder(trackOrderQuery);
        log.info("Returning order status with tracking id: {}", trackOrderResponse.getOrderTackingId() );

        return ResponseEntity.ok( trackOrderResponse );
    }
}
