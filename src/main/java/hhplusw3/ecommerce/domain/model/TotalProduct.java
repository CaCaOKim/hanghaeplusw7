package hhplusw3.ecommerce.domain.model;

import java.util.List;

public record TotalProduct(
    List<Product> products,
    long totalPrice
) {}
