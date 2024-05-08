package hhplusw3.ecommerce.domain.component;

import hhplusw3.ecommerce.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;

    long userId = 1;
    User user;
    List<ProductWithCount> productWithCounts;
    TotalProduct totalProduct;


    @BeforeEach
    void getParameters() {
        // given
        this.user = this.userService.getUser(userId);
        this.productWithCounts = new ArrayList<>();
        this.productWithCounts.add(new ProductWithCount(4, 2));
        this.productWithCounts.add(new ProductWithCount(5, 3));
        this.totalProduct = this.productService.checkProductsStock(productWithCounts);
    }

    @Test
    void order() {
        // when
        Order result = this.orderService.order(user, productWithCounts, totalProduct);

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
        Order order = this.orderService.order(user, productWithCounts, totalProduct);

        // when
        Order result = this.orderService.updateOrderState(order.id(), "complete");

        // then
        assertThat(result.id()).isEqualTo(order.id());
        assertThat(result.userId()).isEqualTo(userId);
        assertThat(result.status()).isEqualTo("complete");
    }

    @Test
    void updateOrderProductState() {
        Order order = this.orderService.order(user, productWithCounts, totalProduct);
        OrderProduct result = this.orderService.updateOrderProductState(order.orderProducts().get(0).id(), "complete");

        assertThat(result.id()).isEqualTo(order.orderProducts().get(0).id());
        assertThat(result.productId()).isEqualTo(order.orderProducts().get(0).productId());
        assertThat(result.status()).isEqualTo("complete");
    }

    @Test
    void completeOrder() {
        // given
        Order order = this.orderService.order(user, productWithCounts, totalProduct);

        // when
        Order result = this.orderService.completeOrder(order);

        // then
        assertThat(result.id()).isEqualTo(order.id());
        assertThat(result.userId()).isEqualTo(userId);
        assertThat(result.status()).isEqualTo("complete");
    }
}