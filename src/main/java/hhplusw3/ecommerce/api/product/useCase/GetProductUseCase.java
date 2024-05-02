package hhplusw3.ecommerce.api.product.useCase;


import hhplusw3.ecommerce.api.product.dto.ProductRes;
import hhplusw3.ecommerce.domain.component.ProductService;
import hhplusw3.ecommerce.domain.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetProductUseCase {

    private final ProductService productService;

    @Autowired
    public GetProductUseCase(ProductService productService) {
        this.productService = productService;
    }

    public ProductRes excute(long id) {
        // 1. 상품 조회
        Product product = this.productService.getProduct(id);
        return new ProductRes(product.id(), product.name(), product.price(), product.stock());
    }
}
