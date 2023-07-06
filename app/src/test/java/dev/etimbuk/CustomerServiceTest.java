package dev.etimbuk;

import dev.etimbuk.models.Customer;
import dev.etimbuk.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class CustomerServiceTest {

    @Autowired private CustomerService service;

    private Customer savedCustomer;
    private Customer expectedCustomerToSave;

    @BeforeEach
    void init() {
        expectedCustomerToSave = new Customer("firstname", "surname", LocalDate.now());
        savedCustomer = service.create(expectedCustomerToSave);
    }

    @Test
    void testCustomerSaved() {
        assertEquals(savedCustomer.getFullName(), expectedCustomerToSave.getFullName());
    }

    @Test
    void testSavedCustomerDeleted() {
        service.deleteById(savedCustomer.getId());
        assertFalse(service.findById(savedCustomer.getId()).isPresent());
    }

    @Test
    void testExistingCustomerUpdated() {
        final var newDate = LocalDate.now().plusDays(100);
        final var existingCustomer = service.findById(savedCustomer.getId()).get();
        existingCustomer.setJoinedOn(newDate);

        final var updatedCustomer = service.update(existingCustomer);
        assertEquals(newDate.toString(), updatedCustomer.get().getJoinedOn().toString());
    }
}
