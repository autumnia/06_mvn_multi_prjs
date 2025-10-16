package com.autumnia.shopsystem.order.service.domain.ports.output;

import com.autumnia.shopsystem.order.service.domain.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Optional<Customer> findCustomer(UUID customerId);

}
