package dev.etimbuk;

import dev.etimbuk.models.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JPAHibernateTest {
    private SessionFactory sessionFactory;

    @BeforeEach
    protected void init() {
        final var serviceRegistryProps = new Properties();
        serviceRegistryProps.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/blogposts");
        serviceRegistryProps.put("hibernate.connection.username", "yourusername");
        serviceRegistryProps.put("hibernate.connection.password", "yourpassword");
        serviceRegistryProps.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        serviceRegistryProps.put("hibernate.format_sql", "true");
        serviceRegistryProps.put("hibernate.use_sql_comments", "true");

        // DON'T DO THIS IN PRODUCTION
        serviceRegistryProps.put("hibernate.hbm2ddl.auto", "create-drop");

        final var standardServiceRegistry = new StandardServiceRegistryBuilder().applySettings(serviceRegistryProps).build();

        try {
            final var metaDataSources = new MetadataSources(standardServiceRegistry);
            metaDataSources.addAnnotatedClass(Customer.class);

            sessionFactory = metaDataSources.buildMetadata().buildSessionFactory();

        } catch (Exception exception) {
            StandardServiceRegistryBuilder.destroy(standardServiceRegistry);
        }
    }

    @AfterEach
    protected void cleanUp() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Test
    void save_and_retrieve_customer_success() {
        final var customer = new Customer("Test.User", "Test User", LocalDate.now().minusYears(5));

        try (final Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(customer);
            session.getTransaction().commit();

            List<Customer> savedCustomers = session.createQuery("SELECT c FROM customers c", Customer.class).getResultList();

            assertEquals(1, savedCustomers.size());
        }
    }

}
