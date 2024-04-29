package hhplusw3.ecommerce;

import hhplusw3.ecommerce.domain.reository.OrderRepository;
import hhplusw3.ecommerce.domain.reository.ProductRepository;
import hhplusw3.ecommerce.domain.reository.UserRepository;
import hhplusw3.ecommerce.infrastructure.database.OrderRepositoryJpa;
import hhplusw3.ecommerce.infrastructure.database.ProductRepositoryJpa;
import hhplusw3.ecommerce.infrastructure.database.UserRepositoryJpa;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private EntityManager entityManager;

    public SpringConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Bean
    public UserRepository userRepository() {
        return new UserRepositoryJpa(entityManager);
    }

    @Bean
    public ProductRepository productRepository() {
        return new ProductRepositoryJpa(entityManager);
    }

    @Bean
    public OrderRepository orderRepository() {
        return new OrderRepositoryJpa(entityManager);
    }
}
