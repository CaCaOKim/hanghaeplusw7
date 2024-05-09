package hhplusw3.ecommerce.domain.model;

public record ProductWithCount(
    long productId,
    Product product,
    long count
) {}
