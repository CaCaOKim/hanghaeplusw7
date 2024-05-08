package hhplusw3.ecommerce.infrastructure.jpaRepoExt;

import hhplusw3.ecommerce.infrastructure.entity.OrderProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductJpaRepoExt extends JpaRepository<OrderProductEntity, Long> {
    public List<OrderProductEntity> findByOrderId(long orderId);
}
