package hhplusw3.ecommerce.api.order.dto;


import java.util.List;

public record OrderProductRes(
        long id,
        long orderId,
        long productId,
        String productNm,
        long count,
        String status
) {
}
