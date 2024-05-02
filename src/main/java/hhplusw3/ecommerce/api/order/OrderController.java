package hhplusw3.ecommerce.api.order;

import hhplusw3.ecommerce.api.order.dto.OrderProductReq;
import hhplusw3.ecommerce.api.order.dto.OrderRes;
import hhplusw3.ecommerce.api.order.useCase.OrderUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {

    private final OrderUseCase orderUseCase;

    @Autowired
    OrderController(OrderUseCase orderUseCase) {
        this.orderUseCase = orderUseCase;
    }

    // 주문
    @PostMapping("/{userId}")
    public OrderRes orderProducts(@PathVariable long userId, @RequestBody List<OrderProductReq> orderProduts) throws InterruptedException {
        return this.orderUseCase.excute(userId, orderProduts);
    }

}
