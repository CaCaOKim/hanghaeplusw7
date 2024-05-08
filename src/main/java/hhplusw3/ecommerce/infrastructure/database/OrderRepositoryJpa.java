package hhplusw3.ecommerce.infrastructure.database;

import hhplusw3.ecommerce.domain.model.Order;
import hhplusw3.ecommerce.domain.model.OrderProduct;
import hhplusw3.ecommerce.domain.reository.OrderRepository;
import hhplusw3.ecommerce.infrastructure.entity.OrderEntity;
import hhplusw3.ecommerce.infrastructure.entity.OrderProductEntity;
import hhplusw3.ecommerce.infrastructure.entity.ProductEntity;
import hhplusw3.ecommerce.infrastructure.entity.UserEntity;
import hhplusw3.ecommerce.infrastructure.jpaRepoExt.OrderJpaRepoExt;
import hhplusw3.ecommerce.infrastructure.jpaRepoExt.OrderProductJpaRepoExt;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepositoryJpa implements OrderRepository {
//    private final EntityManager em;
//    public OrderRepositoryJpa(EntityManager em) {
//        this.em = em;
//    }

private final OrderJpaRepoExt orderJpaRepo;
private final OrderProductJpaRepoExt orderProductJpaRepo;

public OrderRepositoryJpa(OrderJpaRepoExt orderJpaRepo, OrderProductJpaRepoExt orderProductJpaRepo) {
    this.orderJpaRepo = orderJpaRepo;
    this.orderProductJpaRepo = orderProductJpaRepo;
}


    @Override
    public Order getOrder(long id) {
        OrderEntity orderEntity = this.orderJpaRepo.findById(id).orElseThrow(EntityNotFoundException::new);
        return new Order(orderEntity.getId(), orderEntity.getUser().getId(), orderEntity.getUser().getName(), orderEntity.getTotalPrice(), orderEntity.getStatus(), this.getOrderProducts(orderEntity.getId()));
    }

    @Override
    public Order order(Order order) {
        OrderEntity orderEntity = new OrderEntity();
        UserEntity user = new UserEntity();
        user.setId(order.userId());

        orderEntity.setId(order.id());
        orderEntity.setUser(user);
        orderEntity.setTotalPrice(order.totalPrice());
        orderEntity.setStatus(order.status());
        OrderEntity result = this.orderJpaRepo.save(orderEntity);
        return new Order(result.getId(), result.getUser().getId(), result.getUser().getName(), result.getTotalPrice(), result.getStatus(), null);
    }

    @Override
    public List<OrderProduct> getOrderProducts(long orderId) {
//        String query = "select p from OrderProductEntity p where p.orderId = :orderId";
//        List<OrderProductEntity> orderProductEntities = em.createQuery(query, OrderProductEntity.class)
//                .setParameter("orderId", orderId)
//                .getResultList();
        List<OrderProductEntity> orderProductEntities = this.orderProductJpaRepo.findByOrderId(orderId);
        return orderProductEntities.stream().map(p -> new OrderProduct(p.getId(), p.getOrderId(), p.getProduct().getId(), p.getProduct().getName(), p.getCount(), p.getStatus())).toList();
    }

    @Override
    public OrderProduct getOrderProduct(long id) {
        OrderProductEntity orderProductEntity = this.orderProductJpaRepo.findById(id).orElseThrow(EntityNotFoundException::new);
        return new OrderProduct(orderProductEntity.getId(), orderProductEntity.getOrderId(), orderProductEntity.getProduct().getId(), orderProductEntity.getProduct().getName(), orderProductEntity.getCount(), orderProductEntity.getStatus());
    }

    @Override
    public OrderProduct orderProduct(OrderProduct orderProduct) {
        OrderProductEntity orderProductEntity = new OrderProductEntity();
        orderProductEntity.setId(orderProduct.id());
        ProductEntity product = new ProductEntity();
        product.setId(orderProduct.productId());
        orderProductEntity.setOrderId(orderProduct.orderId());
        orderProductEntity.setProduct(product);
        orderProductEntity.setCount(orderProduct.count());
        orderProductEntity.setStatus(orderProduct.status());
        this.orderProductJpaRepo.save(orderProductEntity);
        return new OrderProduct(orderProductEntity.getId(), orderProductEntity.getOrderId(), orderProductEntity.getProduct().getId(), orderProductEntity.getProduct().getName(), orderProductEntity.getCount(), orderProductEntity.getStatus());
    }
    
}
