package hhplusw3.ecommerce.api.product;

import hhplusw3.ecommerce.api.product.dto.ProductRes;
import hhplusw3.ecommerce.api.product.useCase.GetProductUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("product")
public class ProductController {

    private final GetProductUseCase getProductUseCase;

    @Autowired
    ProductController(GetProductUseCase getProductUseCase) {
        this.getProductUseCase = getProductUseCase;
    }

    // 상품 조회
    @GetMapping("/{id}")
    public ProductRes getProduct(@PathVariable long id) throws InterruptedException {
        return this.getProductUseCase.excute(id);
    }

}
