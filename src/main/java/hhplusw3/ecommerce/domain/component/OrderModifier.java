package hhplusw3.ecommerce.domain.component;

import hhplusw3.ecommerce.domain.model.Order;
import hhplusw3.ecommerce.domain.model.OrderProduct;
import hhplusw3.ecommerce.domain.model.TranscationType;
import hhplusw3.ecommerce.domain.model.User;
import hhplusw3.ecommerce.domain.reository.OrderRepository;
import hhplusw3.ecommerce.domain.reository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderModifier {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderModifier(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order orderProducts(Order order) {
        Order result = this.orderRepository.orderProducts(order);
        List<OrderProduct> resultProducts = new ArrayList<>();
        for (OrderProduct orderProduct : order.orderProducts()) {
            resultProducts.add(this.orderRepository.orderProduct(new OrderProduct(orderProduct.id(), result.id(), orderProduct.productId(), orderProduct.productNm(), orderProduct.count(), orderProduct.status())));
        }
        return new Order(result.id(), result.userId(), result.userNm(), result.totalPrice(), result.status(), resultProducts);
    }

    public Order updateOrderState(long orderId, String state) {
        Order order = this.orderRepository.getOrder(orderId);
        Order result = this.orderRepository.updateOrder(new Order(order.id(), order.userId(), order.userNm(), order.totalPrice(), state, null));
        return result;
    }

    public OrderProduct updateOrderProductState(long orderProductId, String state) {
        OrderProduct orderProduct = this.orderRepository.getOrderProduct(orderProductId);
        OrderProduct result = this.orderRepository.updateOrderProduct(new OrderProduct(orderProduct.id(), orderProduct.orderId(), orderProduct.productId(), orderProduct.productNm(), orderProduct.count(), state));
        return result;
    }
}
