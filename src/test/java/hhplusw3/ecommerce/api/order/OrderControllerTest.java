package hhplusw3.ecommerce.api.order;

import hhplusw3.ecommerce.api.order.dto.OrderProductReq;
import hhplusw3.ecommerce.api.order.dto.OrderRes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class OrderControllerTest {

    @Autowired
    OrderController orderController;

    long userId = 1;

    @Test
    void order() throws InterruptedException {
        List<OrderProductReq> orderProductReqs = new ArrayList<>();
        orderProductReqs.add(new OrderProductReq(1, 1));
        orderProductReqs.add(new OrderProductReq(2, 1));

        OrderRes order = this.orderController.orderProducts(userId, orderProductReqs);

        assertThat(order.userId()).isEqualTo(userId);
        assertThat(order.totalPrice()).isEqualTo(28000);
        assertThat(order.status()).isEqualTo("complete");
    }

    @Test
    void userId가_유실되면_주문_실패() throws InterruptedException {
        List<OrderProductReq> orderProductReqs = new ArrayList<>();
        orderProductReqs.add(new OrderProductReq(1, 1));
        orderProductReqs.add(new OrderProductReq(2, 1));
        orderProductReqs.add(new OrderProductReq(3, 1));

        assertThatThrownBy(() -> {
            OrderRes order = this.orderController.orderProducts(0, orderProductReqs);
        }).isInstanceOf(RuntimeException.class);
    }

    @Test
    void 상품이_품절된_경우_주문실패() throws InterruptedException {
        List<OrderProductReq> orderProductReqs = new ArrayList<>();
        orderProductReqs.add(new OrderProductReq(3, 5));

        assertThatThrownBy(() -> {
            OrderRes order = this.orderController.orderProducts(userId, orderProductReqs);
        }).isInstanceOf(RuntimeException.class);
    }

    @Test
    void 유저의_잔액이_부족한_경우_주문실패() throws InterruptedException {
        List<OrderProductReq> orderProductReqs = new ArrayList<>();
        orderProductReqs.add(new OrderProductReq(1, 3));
        orderProductReqs.add(new OrderProductReq(2, 2));

        assertThatThrownBy(() -> {
            OrderRes order = this.orderController.orderProducts(2, orderProductReqs);
        }).isInstanceOf(RuntimeException.class);
    }
}