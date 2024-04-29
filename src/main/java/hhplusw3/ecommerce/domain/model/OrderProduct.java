package hhplusw3.ecommerce.domain.model;

public record OrderProduct(
    long id,
    long orderId,
    long productId,
    String productNm,
    long count,
    String status
) {}
