package hhplusw3.ecommerce.api.order.useCase;


import hhplusw3.ecommerce.api.order.dto.OrderProductReq;
import hhplusw3.ecommerce.api.order.dto.OrderProductRes;
import hhplusw3.ecommerce.api.order.dto.OrderRes;
import hhplusw3.ecommerce.domain.component.*;
import hhplusw3.ecommerce.domain.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class OrderProductsUseCase {

    private final OrderModifier orderModifier;
    private final ProductReader productReader;
    private final ProductModifier productModifier;
    private final UserReader userReader;
    private final UserModifier userModifier;

    @Autowired
    public OrderProductsUseCase(OrderModifier orderModifier, ProductReader productReader, ProductModifier productModifier, UserReader userReader, UserModifier userModifier) {
        this.orderModifier = orderModifier;
        this.productReader = productReader;
        this.productModifier = productModifier;
        this.userReader = userReader;
        this.userModifier = userModifier;
    }

    Map<Long, Lock> lockMap = new ConcurrentHashMap<>();

    public OrderRes excute(long userId, List<OrderProductReq> orderProdutReqs) {
        List<Product> products = new ArrayList<>();
        List<OrderProduct> orderProducts = new ArrayList<>();
        long totalPrice = 0;
        for (OrderProductReq orderProductReq : orderProdutReqs) {
            Lock lock = lockMap.computeIfAbsent(orderProductReq.productId(), key -> new ReentrantLock());
            lock.lock();
            try {
                Product product = this.productReader.getProduct(orderProductReq.productId());
                if (product.stock() > orderProductReq.count()) {
                    totalPrice += product.price() * orderProductReq.count();
                    Product afterProduct = new Product(product.id(), product.name(), product.price(), product.stock() - orderProductReq.count(), product.sales() + orderProductReq.count());
                    this.productModifier.updateProduct(afterProduct);
                    products.add(afterProduct);
                    orderProducts.add(new OrderProduct(0, 0, orderProductReq.productId(), product.name(), orderProductReq.count(), "ready"));
                }
            } finally {
                lock.unlock();
            }
        }
        if (totalPrice == 0) {
            throw new RuntimeException("제품들이 모두 품절되었습니다.");
        }
        User user = this.userReader.getUser(userId);
        if (user.money() < totalPrice) {
            for (Product reProduct : products) {
                Optional<OrderProductReq> opr = orderProdutReqs.stream().filter(p -> p.productId() == reProduct.id()).findAny();
                if (opr != null) {
                    this.productModifier.updateProduct(new Product(reProduct.id(), reProduct.name(), reProduct.price(), reProduct.stock() + opr.stream().count(), reProduct.sales() - opr.stream().count()));
                }
            }
            throw new RuntimeException("잔액이 부족합니다.");
        }
        Order order = this.orderModifier.orderProducts(new Order(0, userId, null, totalPrice, "ready", orderProducts));
        orderProducts = order.orderProducts();
        User userResult = new User(0, null, 0);
        if (order.id() > 0) {
            userResult = this.userModifier.calculateMoney(user.id(), totalPrice, TranscationType.USE);
        }
        List<OrderProductRes> orderProductResies = new ArrayList<>();
        if (userResult.id() > 0) {
            order = this.orderModifier.updateOrderState(order.id(), "complete");
            for (OrderProduct orderProduct : orderProducts) {
                orderProduct = this.orderModifier.updateOrderProductState(orderProduct.id(), "complete");
                orderProductResies.add(new OrderProductRes(orderProduct.id(), orderProduct.orderId(), orderProduct.productId(), orderProduct.productNm(), orderProduct.count(), orderProduct.status()));
            }
        } else {
            orderProductResies = order.orderProducts().stream().map(p -> new OrderProductRes(p.id(), p.orderId(), p.productId(), p.productNm(), p.count(), p.status())).toList();
        }
        return new OrderRes(order.id(), order.userId(), order.userNm(), order.totalPrice(), order.status(), orderProductResies);
    }
}
