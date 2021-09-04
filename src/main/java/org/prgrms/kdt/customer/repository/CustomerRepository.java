package org.prgrms.kdt.customer.repository;

import org.prgrms.kdt.customer.domain.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer insert(Customer customer);

    Customer update(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findById(UUID customerId);

    Optional<Customer> findByName(String customerName);

    Optional<Customer> findByEmail(String customerEmail);

    void deleteAll();

    int count();
}