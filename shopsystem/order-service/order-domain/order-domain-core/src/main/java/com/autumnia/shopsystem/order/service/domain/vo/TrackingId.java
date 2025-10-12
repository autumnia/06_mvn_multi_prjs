package com.autumnia.shopsystem.order.service.domain.vo;

import com.autumnia.shopsystem.domain.vo.BaseId;

import java.util.UUID;

public class TrackingId extends BaseId<UUID> {
    public TrackingId(UUID value) {
        super(value);
    }


}
