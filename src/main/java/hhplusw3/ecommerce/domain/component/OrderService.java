package hhplusw3.ecommerce.domain.component;

import hhplusw3.ecommerce.domain.model.*;
import hhplusw3.ecommerce.domain.reository.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order order(User user, List<ProductWithCount> productWithCounts) {
        long totalPrice = 0;
        for (ProductWithCount productWithCount : productWithCounts) {
            totalPrice += productWithCount.product().price() * productWithCount.count();
        }
        Order order = this.orderRepository.order(new Order(0, user.id(), user.name(), totalPrice, "ready", null));
        List<OrderProduct> resultProducts = productWithCounts.stream().map(pc -> this.orderRepository.orderProduct(new OrderProduct(0, order.id(), pc.productId(), pc.product().name(), pc.count(), "ready"))).toList();
        return new Order(order.id(), order.userId(), order.userNm(), order.totalPrice(), order.status(), resultProducts);
    }

    public Order updateOrderState(long orderId, String state) {
        Order order = this.orderRepository.getOrder(orderId);
        Order result = this.orderRepository.order(new Order(order.id(), order.userId(), order.userNm(), order.totalPrice(), state, null));
        return result;
    }

    public OrderProduct updateOrderProductState(long orderProductId, String state) {
        OrderProduct orderProduct = this.orderRepository.getOrderProduct(orderProductId);
        OrderProduct result = this.orderRepository.orderProduct(new OrderProduct(orderProduct.id(), orderProduct.orderId(), orderProduct.productId(), orderProduct.productNm(), orderProduct.count(), state));
        return result;
    }

    public Order completeOrder(Order order) {
        Order result = this.updateOrderState(order.id(), "complete");
        List<OrderProduct> orderProductResults = new ArrayList<>();
        for (OrderProduct orderProduct : order.orderProducts()) {
            orderProductResults.add(this.updateOrderProductState(orderProduct.id(), "complete"));
        }

        return new Order(result.id(), result.userId(), result.userNm(), result.totalPrice(), result.status(), orderProductResults);
    }
}
