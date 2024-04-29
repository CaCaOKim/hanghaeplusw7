package hhplusw3.ecommerce.api.order;

import hhplusw3.ecommerce.api.order.dto.OrderProductReq;
import hhplusw3.ecommerce.api.order.dto.OrderRes;
import hhplusw3.ecommerce.api.order.useCase.OrderProductsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {

    private final OrderProductsUseCase orderProductsUseCase;

    @Autowired
    OrderController(OrderProductsUseCase orderProductsUseCase) {
        this.orderProductsUseCase = orderProductsUseCase;
    }

    // 주문
    @PostMapping("/{userId}")
    public OrderRes orderProducts(@PathVariable long userId, @RequestBody List<OrderProductReq> orderProduts) throws InterruptedException {
        return this.orderProductsUseCase.excute(userId, orderProduts);
    }

}
