package hhplusw3.ecommerce.domain.model;

import java.util.List;

public record Order(
    long id,
    long userId,
    String userNm,
    long totalPrice,
    String status,
    List<OrderProduct> orderProducts
) {}
