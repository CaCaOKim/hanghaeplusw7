package hhplusw3.ecommerce.api.order.dto;


import java.util.List;

public record OrderRes(
        long id,
        long userId,
        String userNm,
        long totalPrice,
        String status,
        List<OrderProductRes> orderProducts
) {
}
