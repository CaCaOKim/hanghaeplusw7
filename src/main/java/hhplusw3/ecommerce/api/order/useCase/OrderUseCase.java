package hhplusw3.ecommerce.api.order.useCase;


import hhplusw3.ecommerce.api.order.dto.OrderProductReq;
import hhplusw3.ecommerce.api.order.dto.OrderProductRes;
import hhplusw3.ecommerce.api.order.dto.OrderRes;
import hhplusw3.ecommerce.domain.component.*;
import hhplusw3.ecommerce.domain.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class OrderUseCase {

    private final OrderService orderService;
    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public OrderUseCase(OrderService orderService, ProductService productService, UserService userService) {
        this.orderService = orderService;
        this.productService = productService;
        this.userService = userService;
    }

    @Transactional
    public OrderRes excute(long userId, List<OrderProductReq> orderProdutReqs) {
        // 1. 상품 조회
        List<ProductWithCount> productWithCounts = orderProdutReqs.stream().map(op -> new ProductWithCount(op.productId(), op.count())).toList();
        TotalProduct totalProduct = this.productService.checkProductsStock(productWithCounts);

        // 2. 잔액 조회
        User user = this.userService.checkUserMoney(userId, totalProduct.totalPrice());

        // 3. 주문
        Order order = this.orderService.order(user, productWithCounts, totalProduct);

        // 4. 재고 차감
        List<Product> products = this.productService.balanceStock(order.orderProducts());

        // 5. 잔액 차감
        User userResult = this.userService.useMoney(user, totalProduct.totalPrice());

        // 6. 주문상태 완료로 변경
        Order orderResult = this.orderService.completeOrder(order);

        List<OrderProductRes> orderProductResies = order.orderProducts().stream().map(op -> new OrderProductRes(op.id(), op.orderId(), op.productId(), op.productNm(), op.count(), op.status())).toList();
        return new OrderRes(order.id(), order.userId(), order.userNm(), order.totalPrice(), order.status(), orderProductResies);
    }
}
