package com.autumnia.shopsystem.order.service.domain.dto.message;


import com.autumnia.shopsystem.domain.vo.OrderApprovalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class RestaurantApprovalResponse {
    private String id;
    private String sagaId;
    private String orderId;
    private String restaurandId;
    private Instant createdAt;
    private OrderApprovalStatus orderApprovalStatus;
    private List<String> failuar ;
}
