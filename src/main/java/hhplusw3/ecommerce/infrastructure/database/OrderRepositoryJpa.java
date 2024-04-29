package hhplusw3.ecommerce.infrastructure.database;

import hhplusw3.ecommerce.domain.model.Order;
import hhplusw3.ecommerce.domain.model.OrderProduct;
import hhplusw3.ecommerce.domain.reository.OrderRepository;
import hhplusw3.ecommerce.infrastructure.entity.OrderEntity;
import hhplusw3.ecommerce.infrastructure.entity.OrderProductEntity;
import hhplusw3.ecommerce.infrastructure.entity.ProductEntity;
import hhplusw3.ecommerce.infrastructure.entity.UserEntity;
import jakarta.persistence.EntityManager;

import java.util.List;

public class OrderRepositoryJpa implements OrderRepository {
    private final EntityManager em;

    public OrderRepositoryJpa(EntityManager em) {
        this.em = em;
    }


    @Override
    public Order getOrder(long id) {
        OrderEntity orderEntity = em.find(OrderEntity.class, id);
        return new Order(orderEntity.getId(), orderEntity.getUser().getId(), orderEntity.getUser().getName(), orderEntity.getTotalPrice(), orderEntity.getStatus(), this.getOrderProducts(orderEntity.getId()));
    }

    @Override
    public Order orderProducts(Order order) {
        OrderEntity orderEntity = new OrderEntity();
        UserEntity user = new UserEntity();
        user.setId(order.userId());
        orderEntity.setUser(user);
        orderEntity.setTotalPrice(order.totalPrice());
        orderEntity.setStatus(order.status());
        em.persist(orderEntity);
        return new Order(orderEntity.getId(), orderEntity.getUser().getId(), orderEntity.getUser().getName(), orderEntity.getTotalPrice(), orderEntity.getStatus(), null);
    }

    @Override
    public Order updateOrder(Order order) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(order.id());
        UserEntity user = new UserEntity();
        user.setId(order.userId());
        orderEntity.setUser(user);
        orderEntity.setTotalPrice(order.totalPrice());
        orderEntity.setStatus(order.status());
        em.merge(orderEntity);
        em.flush();
        if (orderEntity.getId() <= 0) {
            order = new Order(0, 0, null, 0, null, null);
        }
        return order;
    }

    @Override
    public List<OrderProduct> getOrderProducts(long orderId) {
        String query = "select p from OrderProductEntity p where p.orderId = :orderId";
        List<OrderProductEntity> orderProductEntities = em.createQuery(query, OrderProductEntity.class)
                .setParameter("orderId", orderId)
                .getResultList();
        return orderProductEntities.stream().map(p -> new OrderProduct(p.getId(), p.getOrderId(), p.getProduct().getId(), p.getProduct().getName(), p.getCount(), p.getStatus())).toList();
    }

    @Override
    public OrderProduct getOrderProduct(long id) {
        OrderProductEntity orderProductEntity = em.find(OrderProductEntity.class, id);
        return new OrderProduct(orderProductEntity.getId(), orderProductEntity.getOrderId(), orderProductEntity.getProduct().getId(), orderProductEntity.getProduct().getName(), orderProductEntity.getCount(), orderProductEntity.getStatus());
    }

    @Override
    public OrderProduct orderProduct(OrderProduct orderProduct) {
        OrderProductEntity orderProductEntity = new OrderProductEntity();
        ProductEntity product = new ProductEntity();
        product.setId(orderProduct.productId());
        orderProductEntity.setOrderId(orderProduct.orderId());
        orderProductEntity.setProduct(product);
        orderProductEntity.setCount(orderProduct.count());
        orderProductEntity.setStatus(orderProduct.status());
        em.persist(orderProductEntity);
        return new OrderProduct(orderProductEntity.getId(), orderProductEntity.getOrderId(), orderProductEntity.getProduct().getId(), orderProductEntity.getProduct().getName(), orderProductEntity.getCount(), orderProductEntity.getStatus());
    }

    @Override
    public OrderProduct updateOrderProduct(OrderProduct orderProduct) {
        OrderProductEntity orderProductEntity = new OrderProductEntity();
        orderProductEntity.setId(orderProduct.id());
        ProductEntity product = new ProductEntity();
        product.setId(orderProduct.productId());
        orderProductEntity.setOrderId(orderProduct.orderId());
        orderProductEntity.setProduct(product);
        orderProductEntity.setCount(orderProduct.count());
        orderProductEntity.setStatus(orderProduct.status());
        em.merge(orderProductEntity);
        em.flush();
        if (orderProductEntity.getId() <= 0) {
            orderProduct = new OrderProduct(0, 0, 0, null, 0, null);
        }
        return orderProduct;
    }
}
