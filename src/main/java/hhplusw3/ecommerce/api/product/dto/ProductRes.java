package hhplusw3.ecommerce.api.product.dto;


public record ProductRes(
        long id,
        String name,
        long price,
        long stock
) {
}
