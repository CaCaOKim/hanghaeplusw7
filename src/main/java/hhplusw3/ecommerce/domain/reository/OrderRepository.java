package hhplusw3.ecommerce.domain.reository;

import hhplusw3.ecommerce.domain.model.Order;
import hhplusw3.ecommerce.domain.model.OrderProduct;
import hhplusw3.ecommerce.domain.model.Product;

import java.util.List;

public interface OrderRepository {
    public Order getOrder(long id);
    public Order orderProducts(Order order);
    public Order updateOrder(Order order);
    public List<OrderProduct> getOrderProducts(long orderId);
    public OrderProduct getOrderProduct(long id);
    public OrderProduct orderProduct(OrderProduct orderProduct);
    public OrderProduct updateOrderProduct(OrderProduct orderProduct);

}
