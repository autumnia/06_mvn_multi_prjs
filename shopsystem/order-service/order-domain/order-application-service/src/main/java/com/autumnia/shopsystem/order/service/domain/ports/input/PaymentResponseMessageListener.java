package com.autumnia.shopsystem.order.service.domain.ports.input;

import com.autumnia.shopsystem.order.service.domain.dto.message.PaymentResponse;

public interface PaymentResponseMessageListener {
    void paymentCompleted(PaymentResponse paymentResponse);
    void paymentCancelled(PaymentResponse paymentResponse);
}
