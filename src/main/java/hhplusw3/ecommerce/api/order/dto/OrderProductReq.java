package hhplusw3.ecommerce.api.order.dto;


public record OrderProductReq(
        long productId,
        long count
) {
}
