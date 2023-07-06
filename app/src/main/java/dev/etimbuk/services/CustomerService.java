package dev.etimbuk.services;

import dev.etimbuk.models.Customer;
import dev.etimbuk.repos.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerService {
    private final CustomerRepository customerRepo;

    public Optional<Customer> findById(final String customerId) {
        return customerRepo.findById(customerId);
    }

    public Customer create(final Customer customer) {
        return customerRepo.save(customer);
    }

    public List<Customer> create(final List<Customer> customers) {
        return customerRepo.saveAll(customers);
    }

    public Optional<Customer> update(final Customer customer) {
        if (findById(customer.getId()).isPresent()) {
            return Optional.of(customerRepo.save(customer));
        }

        return Optional.empty();
    }

    public void deleteById(final String customerId) {
        if (findById(customerId).isPresent()) {
            customerRepo.deleteById(customerId);
        }
    }
}
