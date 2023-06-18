package dev.etimbuk;

import dev.etimbuk.models.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VanillaJPATest {
    private EntityManager entityManager;

    @BeforeEach
    protected void init() {
        final var entityManagerFactory = Persistence.createEntityManagerFactory("vanilla-jpa-unit");
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterEach
    protected void cleanUp() {
        if (entityManager != null) {
            entityManager.close();
        }
    }

    @Test
    void save_and_retrieve_customer_success() {
        final var customer = new Customer("Test.User", "Test User", LocalDate.now().minusYears(5));

        entityManager.getTransaction().begin();
        entityManager.persist(customer);
        entityManager.getTransaction().commit();

        List<Customer> savedCustomers = entityManager.createQuery("SELECT c FROM customers c", Customer.class).getResultList();

        assertEquals(1, savedCustomers.size());
    }

}
