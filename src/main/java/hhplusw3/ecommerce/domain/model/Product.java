package hhplusw3.ecommerce.domain.model;

public record Product(
    long id,
    String name,
    long price,
    long stock,
    long sales
) {}
