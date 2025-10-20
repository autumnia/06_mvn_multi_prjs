package com.autumnia.shopsystem.order.service.domain.dto.track;

import com.autumnia.shopsystem.domain.vo.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class TrackOrderResponse {
    @NotNull
    private final UUID orderTackingId;

    @NotNull
    private final OrderStatus orderStatus;

    private final List<String> failureMessages;
}
