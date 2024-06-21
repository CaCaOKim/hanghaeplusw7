package hhplusw3.ecommerce.domain.component;

import hhplusw3.ecommerce.domain.model.*;
import hhplusw3.ecommerce.domain.reository.OrderRepository;
import hhplusw3.ecommerce.domain.reository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DataJpaTest
@Transactional
class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;
    @InjectMocks
    OrderService orderService;

    // given
    long userId = 1;

    @Test
    void order() {
        // given
        User user = new User(1, "robert", 30000);
        List<ProductWithCount> productWithCounts = new ArrayList<>();
        productWithCounts.add(new ProductWithCount(4, new Product(4, "bottle4", 1000, 10, 300), 2));
        productWithCounts.add(new ProductWithCount(5, new Product(5, "bottle5", 2000, 100, 200), 3));

        // when
        when(orderRepository.order(new Order(0, user.id(), user.name(), 8000, "ready", null))).thenReturn(new Order(1, user.id(), user.name(), 8000, "ready", null));
        when(orderRepository.orderProduct(new OrderProduct(0, 1, 4, "bottle4", 2, "ready"))).thenReturn(new OrderProduct(1, 1, 4, "bottle4", 2, "ready"));
        when(orderRepository.orderProduct(new OrderProduct(0, 1, 5, "bottle5", 3, "ready"))).thenReturn(new OrderProduct(2, 1, 5, "bottle5", 3, "ready"));
        Order result = this.orderService.order(user, productWithCounts);

        // then
        assertThat(result.userId()).isEqualTo(userId);
        assertThat(result.totalPrice()).isEqualTo(8000);
        assertThat(result.status()).isEqualTo("ready");
        assertThat(result.orderProducts().get(0).productId()).isEqualTo(4);
        assertThat(result.orderProducts().get(0).count()).isEqualTo(2);
        assertThat(result.orderProducts().get(0).status()).isEqualTo("ready");
        assertThat(result.orderProducts().get(1).productId()).isEqualTo(5);
        assertThat(result.orderProducts().get(1).count()).isEqualTo(3);
        assertThat(result.orderProducts().get(1).status()).isEqualTo("ready");
    }

    @Test
    void updateOrderState() {
        // given
        long orderId = 1;
        String state = "complete";

        // when
        List<OrderProduct> orderProducts = new ArrayList<>();
        orderProducts.add(new OrderProduct(1, 1, 4, "bottle4", 2, "ready"));
        orderProducts.add(new OrderProduct(2, 1, 5, "bottle5", 3, "ready"));
        when(orderRepository.getOrder(orderId)).thenReturn(new Order(1, userId, "robert", 8000, "ready", orderProducts));
        when(orderRepository.order(new Order(1, userId, "robert", 8000, "complete", null))).thenReturn(new Order(1, userId, "robert", 8000, "complete", null));
        Order result = this.orderService.updateOrderState(orderId, state);

        // then
        assertThat(result.id()).isEqualTo(orderId);
        assertThat(result.userId()).isEqualTo(userId);
        assertThat(result.status()).isEqualTo("complete");
    }

    @Test
    void updateOrderProductState() {
        // given
        long orderProductId = 1;
        String state = "complete";

        // when
        when(orderRepository.getOrderProduct(orderProductId)).thenReturn(new OrderProduct(1, 1, 4, "bottle4", 2, "ready"));
        when(orderRepository.orderProduct(new OrderProduct(1, 1, 4, "bottle4", 2, state))).thenReturn(new OrderProduct(1, 1, 4, "bottle4", 2, "complete"));
        OrderProduct result = this.orderService.updateOrderProductState(orderProductId, state);

        // then
        assertThat(result.id()).isEqualTo(orderProductId);
        assertThat(result.productId()).isEqualTo(4);
        assertThat(result.status()).isEqualTo(state);
    }

    @Test
    void completeOrder() {
        // given
        List<OrderProduct> orderProducts = new ArrayList<>();
        orderProducts.add(new OrderProduct(1, 1, 4, "bottle4", 2, "ready"));
        orderProducts.add(new OrderProduct(2, 1, 5, "bottle5", 3, "ready"));
        Order order = new Order(1, 1, "robert", 8000, "ready", orderProducts);

        // when
        when(orderRepository.getOrder(order.id())).thenReturn(new Order(1, userId, "robert", 8000, "ready", orderProducts));
        when(orderRepository.order(new Order(1, userId, "robert", 8000, "complete", null))).thenReturn(new Order(1, userId, "robert", 8000, "complete", null));
        when(orderRepository.getOrderProduct(orderProducts.get(0).id())).thenReturn(new OrderProduct(1, 1, 4, "bottle4", 2, "ready"));
        when(orderRepository.orderProduct(new OrderProduct(orderProducts.get(0).id(), 1, 4, "bottle4", 2, "complete"))).thenReturn(new OrderProduct(1, 1, 4, "bottle4", 2, "complete"));
        when(orderRepository.getOrderProduct(orderProducts.get(1).id())).thenReturn(new OrderProduct(2, 1, 5, "bottle5", 3, "ready"));
        when(orderRepository.orderProduct(new OrderProduct(orderProducts.get(1).id(), 1, 5, "bottle5", 3, "complete"))).thenReturn(new OrderProduct(2, 1, 5, "bottle5", 3, "complete"));
        Order result = this.orderService.completeOrder(order);

        // then
        assertThat(result.id()).isEqualTo(order.id());
        assertThat(result.userId()).isEqualTo(userId);
        assertThat(result.status()).isEqualTo("complete");
    }
}