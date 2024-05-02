package hhplusw3.ecommerce.api.product.useCase;


import hhplusw3.ecommerce.api.product.dto.ProductRes;
import hhplusw3.ecommerce.domain.component.ProductService;
import hhplusw3.ecommerce.domain.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetTopProductsUseCase {

    private final ProductService productService;

    @Autowired
    public GetTopProductsUseCase(ProductService productService) {
        this.productService = productService;
    }

    public List<ProductRes> excute(long topNum, String soldOutYn) {
        // 1. 상위 상품 조회
        List<Product> products = this.productService.getTopProducts(topNum, soldOutYn);
        return products.stream().map(p -> new ProductRes(p.id(), p.name(), p.price(), p.stock())).toList();
    }
}
