package com.autumnia.shopsystem.order.service.domain.exception;

import com.autumnia.shopsystem.domain.exception.DomainException;

public class OrderNotFoundException extends DomainException  {
    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
