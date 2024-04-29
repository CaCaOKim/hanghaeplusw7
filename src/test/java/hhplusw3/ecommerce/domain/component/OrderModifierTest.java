package hhplusw3.ecommerce.domain.component;

import hhplusw3.ecommerce.api.order.dto.OrderProductReq;
import hhplusw3.ecommerce.api.order.dto.OrderRes;
import hhplusw3.ecommerce.domain.model.Order;
import hhplusw3.ecommerce.domain.model.OrderProduct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderModifierTest {

    @Autowired
    OrderModifier orderModifier;

    long userId = 1;

    @Test
    void orderProducts() {
        List<OrderProduct> orderProducts = new ArrayList<>();
        orderProducts.add(new OrderProduct(0, 0, 1, null, 2, "ready"));
        orderProducts.add(new OrderProduct(0, 0, 2, null, 3, "ready"));
        Order order = new Order(0, userId, null, 69000, "ready", orderProducts);

        Order result = this.orderModifier.orderProducts(order);

        assertThat(result.userId()).isEqualTo(userId);
        assertThat(result.totalPrice()).isEqualTo(69000);
        assertThat(result.status()).isEqualTo("ready");
        assertThat(result.orderProducts().get(0).productId()).isEqualTo(order.orderProducts().get(0).productId());
        assertThat(result.orderProducts().get(0).count()).isEqualTo(order.orderProducts().get(0).count());
        assertThat(result.orderProducts().get(0).status()).isEqualTo(order.orderProducts().get(0).status());
        assertThat(result.orderProducts().get(1).productId()).isEqualTo(order.orderProducts().get(1).productId());
        assertThat(result.orderProducts().get(1).count()).isEqualTo(order.orderProducts().get(1).count());
        assertThat(result.orderProducts().get(1).status()).isEqualTo(order.orderProducts().get(1).status());
    }

    @Test
    void updateOrderState() {
        List<OrderProduct> orderProducts = new ArrayList<>();
        orderProducts.add(new OrderProduct(0, 0, 1, null, 2, "ready"));
        Order order = this.orderModifier.orderProducts(new Order(0, userId, null, 30000, "ready", orderProducts));
        Order result = this.orderModifier.updateOrderState(order.id(), "complete");

        assertThat(result.id()).isEqualTo(order.id());
        assertThat(result.userId()).isEqualTo(userId);
        assertThat(result.status()).isEqualTo("complete");
    }

    @Test
    void updateOrderProductState() {
        List<OrderProduct> orderProducts = new ArrayList<>();
        orderProducts.add(new OrderProduct(0, 0, 1, null, 2, "ready"));
        Order order = this.orderModifier.orderProducts(new Order(0, userId, null, 30000, "ready", orderProducts));
        OrderProduct result = this.orderModifier.updateOrderProductState(order.orderProducts().get(0).id(), "complete");

        assertThat(result.id()).isEqualTo(order.orderProducts().get(0).id());
        assertThat(result.productId()).isEqualTo(order.orderProducts().get(0).productId());
        assertThat(result.status()).isEqualTo("complete");
    }
}